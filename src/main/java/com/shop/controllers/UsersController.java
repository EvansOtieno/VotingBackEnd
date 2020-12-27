package com.shop.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.models.AuthenticationRequest;
import com.shop.models.AuthenticationResponse;
import com.shop.models.Ballot;
import com.shop.models.Contestant;
import com.shop.models.Student;
import com.shop.models.Users;
import com.shop.models.VoteTime;
import com.shop.services.ContestantService;
import com.shop.services.JwtUtil;
import com.shop.services.MyUserDetailsService;
import com.shop.services.StudentService;
import com.shop.services.Time;
import com.shop.services.UserService;

import lombok.Data;

@CrossOrigin(origins ="*", maxAge=3600)
@RestController
@RequestMapping("/")
public class UsersController { 

	@Autowired
	UserService us;
	@Autowired
	ContestantService cs;
	@Autowired
	StudentService ss;
	@Autowired
	Time ts;
	@Autowired
	private AuthenticationManager am;
	@Autowired
	private MyUserDetailsService userdetails;
	@Autowired
	private JwtUtil jwtTokenUtil;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public @Data class msg{
		public String message;
		public msg(String message) {
			this.message=message;
		}
	}

	@ExceptionHandler
	void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
	    response.sendError(HttpStatus.BAD_REQUEST.value());
	}
	
	@GetMapping("/result") 
	public ResponseEntity<?> gf() throws Exception {
		List<Users> users = us.findAll();
		return  ResponseEntity.ok(users);

	}
	
	@GetMapping("/reset") 
	public ResponseEntity<?> reset() throws Exception {
		us.reset();
		return  ResponseEntity.ok(new msg("Database Reset Successful"));

	}

	@PostMapping(value = "/auth/login")
	public ResponseEntity<?> au(@RequestBody Users user) throws Exception {
		try {
			am.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorect username or password");
		}
		final Users users = (Users) userdetails.loadUserByUsername(user.getUsername());
		
		final String jwt = jwtTokenUtil.generateToken(users);
		return ResponseEntity.ok(new AuthenticationResponse(jwt, users));
	}

	@PostMapping(value = "/auth/register")
	public ResponseEntity<?> register(@RequestBody Student std) throws Exception {
		if(us.existsByEmail(std.getEmail())) {
			return ResponseEntity.badRequest().body(new msg("User with the email already exists"));
		}
		std.setRole("Student");
		std.setVoted(false); 
		std.setPassword(passwordEncoder.encode(std.getPassword()));
		ss.save(std);
		return ResponseEntity.ok(new msg("Registration Successful"));
	}

	@GetMapping("admin/users")
	public ResponseEntity<List<Users>> get() throws Exception {
		List<Users> users = us.findAll();
		return new ResponseEntity<List<Users>>(users, HttpStatus.OK);

	}

	@GetMapping("/user/{id}")
	public ResponseEntity<Users> get1(@PathVariable("id") int id) throws Exception {
		
		Users user = us.findById(id);
		return new ResponseEntity<Users>(user, HttpStatus.OK);

	}

	@DeleteMapping("/admin/user/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") int id) {
		us.delete(id);
		return new ResponseEntity<String>("Deleted", HttpStatus.OK);

	}

	@PostMapping("/user")
	public ResponseEntity<Users> save(Users user) throws Exception {
		us.save(user);
		return new ResponseEntity<Users>(user, HttpStatus.OK);
	}

	@PostMapping("/admin/contestant")
	public ResponseEntity<Contestant> saveContestant(@RequestParam("image") MultipartFile file,
			@RequestParam("contestant") String contestant) throws Exception {
		Contestant cont = new ObjectMapper().readValue(contestant, Contestant.class);
		Student std = ss.findById(cont.getId());
		std.setRole("Contestant");
		ss.save(std);

		cont.setExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
		cont.setPicture(file.getBytes());
		cs.save(cont);
		return new ResponseEntity<Contestant>(cont, HttpStatus.OK);
	}

	@PostMapping("/student")
	public ResponseEntity<Student> saveStudent(@RequestBody Student std) throws Exception {
		std.setRole("student");
		ss.save(std);
		return new ResponseEntity<Student>(std, HttpStatus.OK);
	}
	
	@PostMapping("/updateuser")
	public ResponseEntity<?> updateStudent(@RequestBody int id) throws Exception {
		Student std=ss.findById(id);
		std.setRole("Student");
		ss.save(std);
		return new ResponseEntity<Student>(std, HttpStatus.OK);
	}
	
	
	@PostMapping("/updatepassword")
	public ResponseEntity<?> updatePassword(@RequestBody Users std) throws Exception {
		Users u=us.findById(std.getId());
		u.setPassword(passwordEncoder.encode(std.getPassword()));
		us.save(u);
		return new ResponseEntity<Users>(u, HttpStatus.OK);
	}


	@GetMapping("/student")
	public ResponseEntity<List<Student>> getStudent() throws Exception {
		List<Student> std = ss.findAll();
		return new ResponseEntity<List<Student>>(std, HttpStatus.OK);
	}

	@GetMapping("/studente/{email}")
	public ResponseEntity<?> getStudent1(@PathVariable("email") String email) throws Exception {
		if(!us.existsByEmail(email)) {
			return ResponseEntity.badRequest().body(new msg("Student with that email does not exist"));
		}else {
		Student std = ss.findByEmail(email);
		return new ResponseEntity<Student>(std, HttpStatus.OK);}

	}

	@GetMapping("/student/{id}")
	public ResponseEntity<Student> getStudent1(@PathVariable("id") int id) throws Exception {
		Student std = ss.findById(id);
		return new ResponseEntity<Student>(std, HttpStatus.OK);

	}
	@DeleteMapping("/student/{id}")
	public ResponseEntity<?> deleteStudent1(@PathVariable("id") int id) throws Exception {
		ss.delete(id);
		return ResponseEntity.ok(new String("Student Removed"));

	}
	
	@DeleteMapping("/contestant/{id}")
	public ResponseEntity<?> deleteContestant(@PathVariable("id") int id) throws Exception {
		cs.delete(id);
		return ResponseEntity.ok(new String("Contestant Removed"));

	}
	@GetMapping("/contestant/{id}")
	public ResponseEntity<?> getcont(@PathVariable("id") int id) throws Exception {
		Contestant c=cs.findById(id);
		
		
		return ResponseEntity.ok(tobase64(c));

	}
	
	@PostMapping("/cast") 
	public ResponseEntity<Ballot> cast(@RequestBody Ballot blt) throws Exception {
		
		List<Contestant> cont = new ArrayList<Contestant>();

		if (blt.getAcademics() != null) {
			cont.add(blt.getAcademics());
		}
		if (blt.getChair() != null) {
			cont.add(blt.getChair());
		}
		if (blt.getVchair() != null) {
			cont.add(blt.getVchair());
		}
		if (blt.getFaculty() != null) {
			cont.add(blt.getFaculty());
		}
		if (blt.getSecgen() != null) {
			cont.add(blt.getSecgen());
		}
		if (blt.getHalls() != null) {
			cont.add(blt.getHalls());
		}
		for (Contestant id : cont) {
			cs.updatevotes(id.getId());
		}
		ss.updatevotingstatus(blt.getVoter()); 
		return new ResponseEntity<Ballot>(blt, HttpStatus.OK);
	}

	@GetMapping("/contestant")
	public ResponseEntity<List<Contestant>> getContestant() throws Exception {
		List<Contestant> cont = cs.findAll();
		for (Contestant co : cont) {
			Student a = ss.findById(co.getId());
			co.setName(a.getFirstname() + " " + a.getLastname());
			co.setEmail(a.getEmail());
			tobase64(co);
		}
		return new ResponseEntity<List<Contestant>>(cont, HttpStatus.OK);

	}
	
	@GetMapping("/count")
	public ResponseEntity<?> count() throws Exception {
		return ResponseEntity.ok(ss.count());
	}
	
	@GetMapping("/time")
	public ResponseEntity<?> gt() throws Exception {
		VoteTime vt=ts.find();
		return  ResponseEntity.ok(vt);
	}
	
	@PostMapping("/time")
	public ResponseEntity<VoteTime> saveTime(@RequestBody VoteTime vt) throws Exception {
		ts.save(vt);
		return new ResponseEntity<VoteTime>(vt, HttpStatus.OK);
	}

	@GetMapping("/results")
	public ResponseEntity<List<Contestant>> getContestants() throws Exception {
		List<Contestant> cont = cs.results();
		for (Contestant co : cont) {
			Student a = ss.findById(co.getId());
			co.setName(a.getFirstname() + " " + a.getLastname());
			tobase64(co);
		}

		return new ResponseEntity<List<Contestant>>(cont, HttpStatus.OK);

	}

	public Contestant tobase64(Contestant user) {
		String extension = user.getExtension();

		byte[] blobAsBytes = user.getPicture();
		String base64image = Base64.getEncoder().encodeToString(blobAsBytes);

		user.setBase64image("data:image/" + extension + ";base64," + base64image);

		return user;
	}
}

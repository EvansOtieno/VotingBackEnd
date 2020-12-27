package com.shop.services;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.shop.models.Users;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private MyUserDetailsService userdetailsservice;
	@Autowired
	private JwtUtil jwtutil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authorizationheader = request.getHeader("Authorization");
		String username=null;
		String jwt=null;
		
		if(authorizationheader !=null && authorizationheader.startsWith("Bearer ")) {
			jwt=authorizationheader.substring(7);
			username= jwtutil.extractUsername(jwt);
		}
		
		if(username !=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			Users u=(Users) this.userdetailsservice.loadUserByUsername(username); 
			if(jwtutil.validateToken(jwt, u)) {
				UsernamePasswordAuthenticationToken upat=new UsernamePasswordAuthenticationToken(u, null,u.getAuthorities());
				upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(upat);
			}
		}
		filterChain.doFilter(request,response);
	}
    
}

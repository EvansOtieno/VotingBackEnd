package com.shop.services;

import com.shop.models.VoteTime;

public interface Time {

	VoteTime find();
	VoteTime save(VoteTime vt);
}

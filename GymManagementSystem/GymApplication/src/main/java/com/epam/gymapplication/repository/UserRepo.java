package com.epam.gymapplication.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.gymapplication.entity.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	Optional<User> findByUserName(String userName);
	Optional<User> findByUserNameAndPassword(String userName,String password);

	List<User> findAllByUserNameIn(List<String> trainerUsernames);
	void deleteByUserName(String userName);
	
}

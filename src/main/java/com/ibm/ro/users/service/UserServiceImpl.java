package com.ibm.ro.users.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ibm.ro.users.exception.UserNotFoundException;
import com.ibm.ro.users.model.User;
import com.ibm.ro.users.repo.UserRepo;
import com.ibm.ro.users.ui.dto.UserDTO;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepo userRepo;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	AuthoritiesServiceImpl authoritiesServiceImpl;

	@Override
	public UserDTO getUser(String tcUserId) {
		// Optional<UserEntity> optionalUE = userRepo.findByTcUserId(tcUserId);
		User userEntity = null;// optionalUE.get();
		if (userEntity != null) {
			return modelMapper.map(userEntity, UserDTO.class);
		}
		return null;
	}

	@Override
	public UserDTO createUser(UserDTO userDTO) {

		User userEntity = modelMapper.map(userDTO, User.class);
		userEntity.setEncryptedPass(bCryptPasswordEncoder.encode(userDTO.getPassword()));
		User savedUserEntity = userRepo.save(userEntity);

		return modelMapper.map(savedUserEntity, UserDTO.class);
	}

	@Override
	public UserDTO getUserByName(String name) {
		Optional<User> userEntityOptional = userRepo.findByName(name);
		if (userEntityOptional != null) {
			return modelMapper.map(userEntityOptional.get(), UserDTO.class);
		}
		return null;
	}

	@Override
	public UserDTO getUserByEmail(String email) {
		Optional<User> userEntityOptional = userRepo.findByEmail(email);
		if (userEntityOptional != null) {
			return modelMapper.map(userEntityOptional.get(), UserDTO.class);
		}
		return null;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Optional<User> userEntityOptional = userRepo.findByEmail(email);
		if (userEntityOptional.isEmpty())
			throw new UsernameNotFoundException(email);

		User user = userEntityOptional.get();
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getEncryptedPass(), true,
				true, true, true, authoritiesServiceImpl.getRoles(user.getUsersRoles()));
	}

	@Override
	public void deleteUser(String email) {
		Optional<User> findByEmail = userRepo.findByEmail(email);
		if (findByEmail.isEmpty())
			throw new UserNotFoundException(email);
		User deleteEntity = findByEmail.get();
		userRepo.delete(deleteEntity);
	}

}

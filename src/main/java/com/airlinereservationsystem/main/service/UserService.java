package com.airlinereservationsystem.main.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.airlinereservationsystem.main.exception.InvalidIDException;
import com.airlinereservationsystem.main.model.Customer;
import com.airlinereservationsystem.main.model.User;
import com.airlinereservationsystem.main.repository.CustomerRepository;
import com.airlinereservationsystem.main.repository.UserRepository;


@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private JavaMailSender mailSender;
	
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		  User user = userRepository.findByUsername(username);
		  System.out.println(user);
		  return user;
	}

	public User insert(User user) {
		return userRepository.save(user);
	}
	
	public void sendEmailOnRegistration(int userId) throws InvalidIDException {
	    Optional<Customer> optional = customerRepository.findById(userId);
	    
	    if (!optional.isPresent()) {
	        throw new InvalidIDException("id not found");
	    }
	    
	    Customer customer = optional.get();
	    // Assuming userRepository.findById method requires a parameter, replace it accordingly
	    // User user = userRepository.findById(customer.getId()).orElse(new User());
	    
	    String subject = "Registration confirmation";
	    String text = "Dear " + customer.getName() + ",\n\n" +
	            "Welcome to FlyEase – where every journey begins with excitement and comfort! 🛫✨ We are thrilled to have you on board as a valued member of our community.\n\n" +
	            "As you embark on this digital voyage with us, expect a seamless experience, exclusive offers, and a world of destinations at your fingertips. Your trust in us means the world, and we're committed to providing you with exceptional service from takeoff to landing.\n\n" +
	            "Feel free to explore our user-friendly website, discover amazing travel deals, and effortlessly plan your next adventure. If you ever need assistance or have questions, our dedicated support team is here to help.\n\n" +
	            "Thank you for choosing Airease for your travel needs. Get ready to soar to new heights!\n\n" +
	            "Safe travels and happy exploring!\n\n" +
	            "Warm regards,\n\n" +
	            "Airease Team"; // Add any additional text if needed

	    // Assuming mailSender is an instance of JavaMailSender
	    SimpleMailMessage message = new SimpleMailMessage();
	    message.setTo(customer.getEmail());
	    message.setSubject(subject);
	    message.setText(text);
	    mailSender.send(message);
	}

	public void sendEmailOnBooking(int userId) throws InvalidIDException {
	    Optional<Customer> optional = customerRepository.findById(userId);
	    
	    if (!optional.isPresent()) {
	        throw new InvalidIDException("id not found");
	    }
	    
	    Customer customer = optional.get();
	    // Assuming userRepository.findById method requires a parameter, replace it accordingly
	    // User user = userRepository.findById(customer.getId()).orElse(new User());
	    
	    String subject = "Booking confirmed!!!";
	    String text = "Dear " + customer.getName() + ",\n\n" +
	            "Welcome to FlyEase – Booking Successfully Done Get ready to soar to new heights!\n\n" +
	            "Safe travels and happy exploring!\n\n" +
	            "Warm regards,\n\n" +
	            "Airease Team"; // Add any additional text if needed

	    // Assuming mailSender is an instance of JavaMailSender
	    SimpleMailMessage message = new SimpleMailMessage();
	    message.setTo(customer.getEmail());
	    message.setSubject(subject);
	    message.setText(text);
	    mailSender.send(message);
	}

	
}

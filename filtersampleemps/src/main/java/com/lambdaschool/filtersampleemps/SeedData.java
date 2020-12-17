package com.lambdaschool.filtersampleemps;


import com.github.javafaker.Faker;
import com.lambdaschool.filtersampleemps.models.*;
import com.lambdaschool.filtersampleemps.repos.JobTitleRepo;
import com.lambdaschool.filtersampleemps.repos.RoleRepo;
import com.lambdaschool.filtersampleemps.repos.UserRepo;
import com.lambdaschool.filtersampleemps.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Set;


@Transactional
@ConditionalOnProperty(prefix = "command.line.runner",
                       value = "enabled",
                       havingValue = "true",
                       matchIfMissing = true)
@Component
public class SeedData
		implements CommandLineRunner {

	private final Random random = new Random();
	private final EmployeeService employeeService;
	private final JobTitleRepo    jobTitleRepo;
	private final RoleRepo        roleRepo;
	private final UserRepo        userRepo;

	@Autowired
	public SeedData(
			EmployeeService employeeService,
			JobTitleRepo jobTitleRepo,
			RoleRepo roleRepo,
			UserRepo userRepo
	) {
		this.employeeService = employeeService;
		this.jobTitleRepo    = jobTitleRepo;
		this.roleRepo        = roleRepo;
		this.userRepo        = userRepo;
	}

	@Override
	public void run(String... args)
	throws Exception {
		JobTitle jt1 = new JobTitle();
		jt1.setTitle("Big Boss");
		jobTitleRepo.save(jt1);

		JobTitle jt2 = new JobTitle();
		jt2.setTitle("Wizard");
		jobTitleRepo.save(jt2);

		Employee emp1 = new Employee();
		emp1.setName("CINNAMON");
		emp1.setSalary(80000.00);
		emp1.getEmails()
				.add(new Email("hops@local.com", emp1));
		emp1.getEmails()
				.add(new Email("bunny@hoppin.local", emp1));
		emp1.getJobnames()
				.add(new EmployeeTitles(emp1, jt1, "Stumps"));
		emp1.getJobnames()
				.add(new EmployeeTitles(emp1, jt2, "Stumps"));
		employeeService.save(emp1);

		Employee emp2 = new Employee();
		emp2.setName("BARNBARN");
		emp2.setSalary(80000.00);
		emp2.getEmails()
				.add(new Email("barnbarn@local.com", emp2));
		emp2.getJobnames()
				.add(new EmployeeTitles(emp2, jt1, "Stumps"));
		employeeService.save(emp2);

		Employee emp3 = new Employee();
		emp3.setName("JOHN");
		emp3.setSalary(75000.00);
		employeeService.save(emp3);

		Faker nameFaker = new Faker(new Locale("en-US"));

		// this section gets a unique list of names
		Set<String> empNamesSet = new HashSet<>();
		for (int i = 0; i < 3; i++) {
			empNamesSet.add(nameFaker.name()
					.fullName());
		}

		for (String empName : empNamesSet) {
			Employee employee = new Employee(); // create a new employee object that will be removed at the end of the loop body
			employee.setName(empName); // set the name
			employee.setSalary(50000.00 + (100000.00 * random.nextDouble())); // randomly generate salary from 50000 to 150000

			int randomInt = random.nextInt(10); // random number of emails from 0 - 9
			for (int j = 0; j < randomInt; j++) {
				employee.getEmails()
						.add(new Email(nameFaker.internet()
								.emailAddress(), employee));
			}
			employee.getJobnames()
					.add(new EmployeeTitles(employee, jt1, "Stumps")); // just assigning them to the first job title
			employeeService.save(employee);
		}

		// Adding SeedData for users
		Role r1 = new Role("ADMIN");
		Role r2 = new Role("USER");
		r1 = roleRepo.save(r1);
		r2 = roleRepo.save(r2);

		User u1 = new User("barnbarn", "password");
		u1.getRoles()
				.add(new UserRoles(u1, r1));
		userRepo.save(u1);

		User u2 = new User("admin", "password");
		u2.getRoles()
				.add(new UserRoles(u2, r1));
		userRepo.save(u2);

		// users
		User u3 = new User("cinnamon", "ILuvM4th!");
		u3.getRoles()
				.add(new UserRoles(u3, r2));
		userRepo.save(u3);
	}

}


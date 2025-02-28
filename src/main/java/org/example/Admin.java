package org.example;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Admin extends Staff
{
    public Admin() {
        super();
    }

    @Override
    public int compareTo(@NotNull User o) {
        return 0;
    }

    public static void addUser()
    {
        Scanner S = new Scanner(System.in);
        System.out.println("1. Regular\n2. Contributor\n3. Admin");
        int userType = S.nextInt();
        List<Regular> regulars = IMDB.getInstance().getRegulars();
        List<Contributor> contributors = IMDB.getInstance().getContributors();
        List<Admin> admins = IMDB.getInstance().getAdmins();

        switch (userType)
        {
            case 1 -> {
                System.out.print("Enter email: ");
                String email = S.nextLine();
                System.out.print("Enter password: ");
                String password = S.nextLine();
                System.out.print("Enter username: ");
                String username = S.nextLine();
                System.out.println("Enter age: ");
                String age = S.nextLine();
                System.out.println("Enter country: ");
                String country = S.nextLine();
                System.out.print("Enter name: ");
                String name = S.nextLine();
                System.out.println("Enter gender: ");
                String genderStr = S.nextLine();
                char gender = genderStr != null && !genderStr.isEmpty() ? genderStr.charAt(0) : '\0';
                System.out.println("Enter date of birth: ");
                String birthDateStr = S.nextLine();
                LocalDate birthDate = birthDateStr != null ? LocalDate.parse(birthDateStr) : null;

                Information userInfo = new Builder(email, password)
                        .age(String.valueOf(Integer.parseInt(age)))
                        .country(country)
                        .name(name)
                        .gender(gender)
                        .dateOfBirth(birthDate)
                        .build();

                User newUser = UserFactory.createUser(AccountType.REGULAR, username, String.valueOf(0), userInfo);
                regulars.add((Regular) newUser);
                System.out.println("User added!");
            }
            case 2 -> {
                System.out.print("Enter email: ");
                String email = S.nextLine();
                System.out.print("Enter password: ");
                String password = S.nextLine();
                System.out.print("Enter username: ");
                String username = S.nextLine();
                System.out.println("Enter age: ");
                String age = S.nextLine();
                System.out.println("Enter country: ");
                String country = S.nextLine();
                System.out.print("Enter name: ");
                String name = S.nextLine();
                System.out.println("Enter gender: ");
                String genderStr = S.nextLine();
                char gender = genderStr != null && !genderStr.isEmpty() ? genderStr.charAt(0) : '\0';
                System.out.println("Enter date of birth: ");
                String birthDateStr = S.nextLine();
                LocalDate birthDate = birthDateStr != null ? LocalDate.parse(birthDateStr) : null;

                Information userInfo = new Builder(email, password)
                        .age(String.valueOf(Integer.parseInt(age)))
                        .country(country)
                        .name(name)
                        .gender(gender)
                        .dateOfBirth(birthDate)
                        .build();

                User newUser = UserFactory.createUser(AccountType.CONTRIBUTOR, username, String.valueOf(0), userInfo);
                contributors.add((Contributor) newUser);
                System.out.println("User added!");
            }
            case 3 -> {
                System.out.print("Enter email: ");
                String email = S.nextLine();
                System.out.print("Enter password: ");
                String password = S.nextLine();
                System.out.print("Enter username: ");
                String username = S.nextLine();
                System.out.println("Enter age: ");
                String age = S.nextLine();
                System.out.println("Enter country: ");
                String country = S.nextLine();
                System.out.print("Enter name: ");
                String name = S.nextLine();
                System.out.println("Enter gender: ");
                String genderStr = S.nextLine();
                char gender = genderStr != null && !genderStr.isEmpty() ? genderStr.charAt(0) : '\0';
                System.out.println("Enter date of birth: ");
                String birthDateStr = S.nextLine();
                LocalDate birthDate = birthDateStr != null ? LocalDate.parse(birthDateStr) : null;

                Information userInfo = new Builder(email, password)
                        .age(String.valueOf(Integer.parseInt(age)))
                        .country(country)
                        .name(name)
                        .gender(gender)
                        .dateOfBirth(birthDate)
                        .build();

                User newUser = UserFactory.createUser(AccountType.ADMIN, username, String.valueOf(0), userInfo);
                admins.add((Admin) newUser);
                System.out.println("User added!");
            }
        }
    }

    public static void removeUser()
    {
        Scanner S = new Scanner(System.in);

        System.out.println("Choose user type: ");
        System.out.println("1. Regular\n2. Contributor\n3. Admin");
        int userType = S.nextInt();
        List<Regular> regulars = IMDB.getInstance().getRegulars();
        List<Contributor> contributors = IMDB.getInstance().getContributors();
        List<Admin> admins = IMDB.getInstance().getAdmins();

        switch (userType) {
            case 1 -> {
                System.out.print("Enter username: ");
                String username = S.nextLine();
                boolean found = false;
                for (Regular regular : regulars) {
                    if (username.equalsIgnoreCase(regular.getUsername()))
                    {
                        found = true;
                        regulars.remove(regular);
                        System.out.println("User removed!");
                    }
                }
                if (!found)
                {
                    System.out.println("User not found!");
                }
            }
            case 2 -> {
                System.out.print("Enter username: ");
                String username = S.nextLine();
                boolean found = false;
                for (Contributor contributor : contributors) {
                    if (username.equalsIgnoreCase(contributor.getUsername())) {
                        found = true;
                        contributors.remove(contributor);
                        System.out.println("User removed!");
                    }
                }
                if (!found)
                {
                    System.out.println("User not found!");
                }
            }
            case 3 -> {
                System.out.print("Enter username: ");
                String username = S.nextLine();
                boolean found = false;
                for (Admin admin : admins) {
                    if (username.equalsIgnoreCase(admin.getUsername())) {
                        found = true;
                        admins.remove(admin);
                        System.out.println("User removed!");
                    }
                }
                if (!found)
                {
                    System.out.println("User not found!");
                }
            }
        }
    }
}

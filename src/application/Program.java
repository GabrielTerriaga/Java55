package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import model.entities.Employee;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter file path: ");
		String path = sc.nextLine();
		
		//mexendo no arquivo
		try (BufferedReader br = new BufferedReader(new FileReader(path))){
			
			List<Employee> list = new ArrayList<>();
			
			String line = br.readLine();
			while (line != null) {
				String[] fields = line.split(",");
				list.add(new Employee(fields[0], fields[1], Double.parseDouble(fields[2])));
				line = br.readLine();
			}
			
			//salario de filtro informado pelo usuario
			System.out.print("Enter salary: ");
			double salary = sc.nextDouble();
			
			System.out.println("Email of people whose salary is more than " + String.format("%.2f", salary) + ":");
			
			//comparator email
			Comparator<Employee> comp = (e1, e2) -> e1.getEmail().toUpperCase().compareTo(e2.getEmail().toUpperCase());
			
			//filtro por salario informado pelo usuario
			List<Employee> salaryCond = list.stream()
					.filter(s -> s.getSalary() >= salary)
					.sorted(comp)
					.collect(Collectors.toList());
			//Mostra o email de acordo com o requisito do usuario
			salaryCond.forEach(System.out::println);
			
			//filtro por inicial 'M'
			double sum = list.stream()
					.filter(x -> x.getName().charAt(0) == 'M')
					.map(x -> x.getSalary())
					.reduce(0.0, (x,y) -> x + y);
			
			System.out.println("Sum of salary of people whose name starts with 'M': " + sum);
			
		}
		catch(IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		sc.close();
	}

}

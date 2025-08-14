package com.journaldev.hibernate.main;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;

import com.journaldev.hibernate.model.Employee;
import com.journaldev.hibernate.util.HibernateUtil;

public class HibernateMain {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1:
                    insertarEmpleado();
                    break;
                case 2:
                    listaDeEmpleados();
                    break;
                case 3:
                    actualizarEmpleado();
                    break;
                case 4:
                    eliminarEmpleado();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    HibernateUtil.getSessionFactory().close();
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println("\n=== MENU ===");
        System.out.println("1. Insertar nuevo empleado");
        System.out.println("2. Lista de empleados");
        System.out.println("3. Actualizar empleado");
        System.out.println("4. Eliminar empleado");
        System.out.println("0. Salir");
        System.out.print("Elija opción: ");
    }

    private static void insertarEmpleado() {
        System.out.print("Ingrese nombre: ");
        String nombre = sc.nextLine();

        System.out.print("Ingrese rol: ");
        String rol = sc.nextLine();

        Employee emp = new Employee();
        emp.setName(nombre);
        emp.setRole(rol);
        emp.setInsertTime(new Date());

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(emp);
        session.getTransaction().commit();

        System.out.println("Empleado agregado con ID: " + emp.getId());
    }

    @SuppressWarnings("unchecked")
    private static void listaDeEmpleados() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        List<Employee> empleados = session.createQuery("FROM Employee").list();
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
        } else {
            for (Employee e : empleados) {
                System.out.println("ID: " + e.getId() +
                        ", Nombre: " + e.getName() +
                        ", Rol: " + e.getRole() +
                        ", Insertado: " + e.getInsertTime());
            }
        }

        session.getTransaction().commit();
    }

    private static void actualizarEmpleado() {
        System.out.print("Ingrese el ID del empleado a actualizar: ");
        int id = Integer.parseInt(sc.nextLine());

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        Employee emp = (Employee) session.get(Employee.class, id);
        if (emp == null) {
            System.out.println("Empleado no encontrado.");
            session.getTransaction().commit();
            return;
        }

        System.out.print("Ingrese nuevo nombre (actual: " + emp.getName() + "): ");
        String nuevoNombre = sc.nextLine();
        if (!nuevoNombre.trim().isEmpty()) {
            emp.setName(nuevoNombre);
        }

        System.out.print("Ingrese nuevo rol (actual: " + emp.getRole() + "): ");
        String nuevoRol = sc.nextLine();
        if (!nuevoRol.trim().isEmpty()) {
            emp.setRole(nuevoRol);
        }

        session.update(emp);
        session.getTransaction().commit();
        System.out.println("Empleado actualizado correctamente.");
    }

    private static void eliminarEmpleado() {
        System.out.print("Ingrese el ID del empleado a eliminar: ");
        int id = Integer.parseInt(sc.nextLine());

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        Employee emp = (Employee) session.get(Employee.class, id);
        if (emp == null) {
            System.out.println("Empleado no encontrado.");
        } else {
            session.delete(emp);
            System.out.println("Empleado eliminado correctamente.");
        }

        session.getTransaction().commit();
    }
}
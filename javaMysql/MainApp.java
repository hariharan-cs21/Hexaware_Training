package javaMysql;


import javaMysql.DoctorDao;

public class MainApp {
    public static void main(String[] args) {
        DoctorDao obj = new DoctorDao();
        obj.fetchDoctors();
        obj.insertDoctor(101, "David", "Bekham", "Bones");
        obj.fetchDoctors();
    }
}

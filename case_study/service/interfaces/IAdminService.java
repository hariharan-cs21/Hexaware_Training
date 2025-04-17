package hexaware.case_study.service.interfaces;


import hexaware.case_study.entity.Admin;

public interface IAdminService {
    Admin getAdminById(int adminId);
    Admin getAdminByUsername(String username);
    void registerAdmin(Admin admin);
    void updateAdmin(Admin admin);
    void deleteAdmin(int adminId);
}


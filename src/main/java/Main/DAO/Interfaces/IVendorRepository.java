package Main.DAO.Interfaces;

import Main.Model.Vendor;

import java.util.List;

public interface IVendorRepository extends IRepository<Vendor> {
    public List<Vendor> getAllVendors();
    public void addVendors(Vendor Vendor);
    public void deleteVendor(int id);
    public void editVendor(Vendor vendor);
    public int GetTotalVendors() ;
    }

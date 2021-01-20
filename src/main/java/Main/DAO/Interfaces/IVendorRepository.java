package Main.DAO.Interfaces;

import Main.Model.Vendor;

import java.util.List;

public interface IVendorRepository extends IRepository<Vendor> {
    public List<Vendor> getAllVendors();
    public void addVendors(Vendor Vendor);
    public int GetTotalVendors() ;
    }

package Main.DAO;

import Main.DAO.Interfaces.IVendorRepository;
import Main.Model.Toy;
import Main.Model.Vendor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VendorRepository implements IVendorRepository {
    Connection connection = SingletonConnection.getConnexion();

    @Override
    public List<Vendor> getAllVendors() {
        List<Vendor> Vendorlist = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM vendors");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Vendor vendor = new Vendor(rs.getInt("Id"),
                        rs.getString("Name"),rs.getString("Email")
                        ,rs.getString("Address"),rs.getString("FacebookUrl"),
                        rs.getString("phone"));

                Vendorlist.add(vendor);
//                System.out.println(Toylist);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Vendorlist;
    }

    @Override
    public void addVendors(Vendor Vendor) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Vendors(Id, Name,Address,Email,FacebookUrl) " +
                    "VALUES (? , ? ,? ,? ,?,?)") ;
            ps.setInt(1,Vendor.getId());
            ps.setString(2,Vendor.getName());
            ps.setString(3,Vendor.getAddress());
            ps.setString(4,Vendor.getEmail());

            ps.setString(5,Vendor.getFacebook());
            ps.setString(6,Vendor.getPhone());


            ps.executeUpdate();

            System.out.println("toy ajouté");

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("error");

        }
    }

    @Override
    public void deleteVendor(int id) {
        try {

            PreparedStatement st = connection.prepareStatement("DELETE FROM vendors where Id = ?");
            st.setInt(1, id);
            st.executeUpdate();
            System.out.println("c'est bon");

        } catch (SQLException e) {
            System.out.println("erreur");
        }
    }

    @Override
    public void editVendor(Vendor vendor) {

        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE vendors v SET v.Name=? , v.Email= ?, v.Address= ?, v.FacebookUrl= ?,v.phone=? where v.Id= ?");
            ps.setString(1,vendor.getName());
            ps.setString(2,vendor.getEmail());
            ps.setString(3,vendor.getAddress());
            ps.setString(4,vendor.getFacebook());
            ps.setString(5,vendor.getPhone());
            ps.setInt(6,vendor.getId());


            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    @Override
    public Vendor findById(int id) {
        return null;
    }

    @Override
    public List<Vendor> getAll() {
        return null;
    }

    @Override
    public void add(Vendor entity) {




    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(Vendor entity) {

    }

    @Override
    public int GetTotalVendors() {
        int nbvendors =0 ;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT count(*) as total from vendors ");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                nbvendors=rs.getInt("total") ;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nbvendors;    }
}

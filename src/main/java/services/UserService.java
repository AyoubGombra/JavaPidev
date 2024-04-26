package services;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import models.User;
import test.Main;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserService implements IService <User> {
    private static Connection connection;
    static Main main;
    public UserService(){ connection = MyDatabase.getInstance().getConnection();
    }
    @Override
    public void create(User user) throws SQLException {
        String sql = "insert into user ( name, prenom, salaire, role_user, departement, email, password) values (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {  //requête dynamiques précompilees
            ps.setString(1, user.getName());
            ps.setString(2, user.getPrename());
            ps.setInt(3, user.getSalaire());
            ps.setString(4, user.getRole_Useer());
            ps.setString(5, user.getDepartement());
            ps.setString(6, user.getEmail());
            ps.setString(7, user.getPassword());
            ps.executeUpdate();
        }
        catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void update(User user) throws SQLException {
        String sql = "update user set name = ?, prenom = ?, salaire = ?, role_user = ?, departement = ?, email = ?, password = ? where id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, user.getName());
        ps.setString(2, user.getPrename());
        ps.setInt(3, user.getSalaire());
        ps.setString(4, user.getRole_Useer());
        ps.setString(5, user.getDepartement());
        ps.setString(6, user.getEmail());
        ps.setString(7, user.getPassword());
        ps.setInt(8, user.getId());
        ps.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "delete from user where id = ?";
        PreparedStatement ps =connection.prepareStatement(sql);
        ps.setInt(1,id);
        ps.executeUpdate();
    }

    @Override
    public List<User> read() throws SQLException {
        String sql = "select * from user";  //hadhi requête SQL
        Statement statement = connection.createStatement();  //3malna connextion bel base de donne
        ResultSet rs = statement.executeQuery(sql);  //exécution taa requête sql w nhotouha fi rs (kan bech naamlou ajout wala modif wala sup nhotou executeUpdate fi blaset executeQuery)
        List <User> user = new ArrayList<>();
        while (rs.next()){
            User u = new User();
            u.setId(rs.getInt("id"));
            u.setName(rs.getString("name"));
            u.setPrename(rs.getString("prenom"));
            u.setSalaire(rs.getInt("salaire"));
            u.setRole_Useer(rs.getString("role_user"));
            u.setDepartement(rs.getString("departement"));
            u.setEmail(rs.getString("email"));
            u.setPassword(rs.getString("password"));
            //tw bech nzidou el user fi liste
            user.add(u);
        }
        return user;
    }

}

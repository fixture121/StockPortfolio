package ca.sheridancollege.charanit.database;

import ca.sheridancollege.charanit.beans.ApiStock;
import ca.sheridancollege.charanit.beans.Stock;
import ca.sheridancollege.charanit.beans.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DatabaseAccess {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;

    public User findUserAccount(String email) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM sec_user WHERE email = :email";
        namedParameters.addValue("email", email);
        try {
            return jdbc.queryForObject(query, namedParameters, new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException erdae) {
            return null;
        }
    }

    public List<String> getRolesById(Long userId) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT sec_role.roleName FROM user_role, sec_role WHERE user_role.roleId = sec_role.roleId AND userId = :userId";
        namedParameters.addValue("userId", userId);
        return jdbc.queryForList(query, namedParameters, String.class);
    }

//    public void addUser(String email, String password) {
//        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
//        String query = "INSERT INTO sec_user (email, encryptedPassword, enabled) VALUES (:email, :encryptedPassword, 1)";
//        namedParameters.addValue("email", email);
//        namedParameters.addValue("encryptedPassword", passwordEncoder.encode(password));
//        jdbc.update(query, namedParameters);
//    }

    public void addRole(Long userId, Long roleId) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "INSERT INTO user_role (userId, roleId) VALUES (:userId, :roleId)";
        namedParameters.addValue("userId", userId);
        namedParameters.addValue("roleId", roleId);
        jdbc.update(query, namedParameters);
    }

    /* STOCK
     * -insertStock
     * -deleteStockByTicker
     * -updateStock
     * -getAllStocksList
     * -getStockByTicker
     */

    public void insertStock(Stock stock) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("stockTicker", stock.getTicker());
        namedParameters.addValue("stockCompany", stock.getCompany());
        namedParameters.addValue("stockPrice", stock.getPrice());
        namedParameters.addValue("stockQuantity", stock.getQuantity());
        String query = "INSERT INTO stocks(ticker, company, price, quantity) VALUES (:stockTicker, :stockCompany, :stockPrice, :stockQuantity)";
        int rowsAffected = jdbc.update(query, namedParameters);
        if (rowsAffected > 0) {
            System.out.println("Stock placed into portfolio");
        }
    }

    public void deleteStock(String ticker) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "DELETE FROM stocks WHERE ticker = :ticker";
        namedParameters.addValue("ticker", ticker);
        if (jdbc.update(query, namedParameters) > 0) {
            System.out.println("Deleted stock " + ticker + " from portfolio");
        }
    }

    public void updateStock(Stock updatedStock) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "UPDATE stocks SET ticker = :ticker";
        namedParameters.addValue("ticker", updatedStock.getTicker());
        int rowsAffected = jdbc.update(query, namedParameters);
        if (rowsAffected > 0) {
            System.out.println("Updated stock " + updatedStock.getTicker() + " in the portfolio");
        }
    }

    public List<Stock> getAllStocksList() {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM stocks";
        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Stock>(Stock.class));
    }

    public List<Stock> getStockByTicker(String ticker) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM stocks WHERE ticker = :ticker";
        namedParameters.addValue("ticker", ticker);
        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Stock>(Stock.class));
    }

    /* API
     * -getAllApiStocksList
     * -getIndividualApiStock
     * -saveAllApiStocks
     */

    public List<ApiStock> getAllApiStocksList() {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM apiStocks";
        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<ApiStock>(ApiStock.class));
    }

    public ApiStock getIndividualApiStock(String ticker) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM apiStocks WHERE ticker = :ticker";
        namedParameters.addValue("ticker", ticker);
        return jdbc.queryForObject(query, namedParameters, new BeanPropertyRowMapper<ApiStock>(ApiStock.class));
    }

//    public Long insertApiStock(ApiStock stock) {
//        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
//        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
//        String query = "INSERT INTO apiStocks(ticker, company, price) VALUES (:stockTicker, :stockCompany, :stockPrice)";
//        namedParameters.addValue("stockTicker", stock.getTicker());
//        namedParameters.addValue("stockCompany", stock.getCompany());
//        namedParameters.addValue("stockPrice", stock.getPrice());
//        jdbc.update(query, namedParameters, generatedKeyHolder);
//        return (Long) generatedKeyHolder.getKey();
//    }

    public Number insertApiStock(ApiStock stock) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        String query = "INSERT INTO apiStocks(ticker, company, price) VALUES (:stockTicker, :stockCompany, :stockPrice)";
        namedParameters.addValue("stockTicker", stock.getTicker());
        namedParameters.addValue("stockCompany", stock.getCompany());
        namedParameters.addValue("stockPrice", stock.getPrice());
        jdbc.update(query, namedParameters, generatedKeyHolder);
        return generatedKeyHolder.getKey();
    }


    public void saveAllApiStocks(List<ApiStock> apiStockList) {
        for (ApiStock s : apiStockList) {
            insertApiStock(s);
        }
    }

    public void deleteAllApiStocks() {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "DELETE FROM apiStocks";
        jdbc.update(query, namedParameters);
    }

    public Long count() {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT count(*) FROM apiStocks";
        return jdbc.queryForObject(query, namedParameters, Long.TYPE);
    }

}

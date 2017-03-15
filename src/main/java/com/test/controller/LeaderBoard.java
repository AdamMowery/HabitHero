package com.test.controller;

/**
 * Created by Owner on 3/14/2017.
 */
public class LeaderBoard {

<<<<<<< HEAD


/**
>>>>>>> b6647390569e2f0be3cf3d63f5967f073e121ebe
    /// example A of an inner join
-    String hql =
            -            "select eq.euipmentName,eq.type " +
                    -                    " from Euipment eq " +
                    -                    " where eq.id in (select qt.euipment from Quotation qt where qt.supQuotation = :ids)";
-    Query query = session.createQuery(hql);
-    query.setParameter("ids",id);
-    list = (List<Euipment>)query.list();
-
        -
        -    /// example B of an inner join
        -    hql = "from Product p inner join p.category";
-
        -    Query query = session.createQuery(hql);
-    List<Object[]> listResult = query.list();
-
        -for(
        -    Object[] aRow :listResult)
            -
            -    {
        -        Product product = (Product) aRow[0];
        -        Category category = (Category) aRow[1];
        -        System.out.println(product.getName() + " - " + category.getName());
        -    }
+



=======
    {
        Product product = (Product) aRow[0];
        Category category = (Category) aRow[1];
        System.out.println(product.getName() + " - " + category.getName());
    }
**/

=======
>>>>>>> 1b41dfa759b475654a8a71bf3e6506cb11940d53


}

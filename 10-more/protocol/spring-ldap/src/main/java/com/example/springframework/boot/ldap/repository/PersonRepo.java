package com.example.springframework.boot.ldap.repository;

import com.example.springframework.boot.ldap.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Component;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Component
public class PersonRepo {

    @Autowired
    private LdapTemplate ldapTemplate;

    private class PersonAttributesMapper implements AttributesMapper<Person> {
        @Override
        public Person mapFromAttributes(Attributes attrs) throws NamingException {
            Person person = new Person();
            person.setCommonName((String) attrs.get("cn").get());
            person.setSurname((String) attrs.get("sn").get());
            person.setUid((String) attrs.get("uid").get());
            person.setDescription((String) attrs.get("description").get());
            return person;
        }
    }

    /* 查询 */

    public List<Person> findAll() {
        return ldapTemplate.findAll(Person.class);
    }

    public List<String> searchAllPersonAttr(String attrID) {
        return ldapTemplate.search(
                query().where("objectclass").is("inetOrgPerson"),
                (AttributesMapper<String>) attrs -> (String) attrs.get(attrID).get()
        );
    }

    /**
     * 根据dn查询person
     *
     * @param dn
     * @return
     */
    public Person findPersonByDn(String dn) {
        return ldapTemplate.lookup(dn, new PersonAttributesMapper());
    }


    public List<Person> findPersonsByCn(String cn) {
        return ldapTemplate.search(
                query().where("objectclass").is("inetOrgPerson")
                .and("cn").is(cn)
                ,
                new PersonAttributesMapper()
        );
    }

    /* 新增 */

    /**
     * 在ldap里创建用户
     */
    public void createUser(Person person) {
        Name dn = buildDn(person);
        ldapTemplate.bind(dn, null, buildAttributes(person));
    }

    /* 更新 */

    /**
     * 在ldap里更新用户
     */
    public void updateUser(Person person) {
        Name dn = buildDn(person);
        ldapTemplate.rebind(dn, null, buildAttributes(person));
    }

    /* 删除 */

    /**
     * 在ldap里删除用户
     */
    public void deleteUser(Person person) {
        Name dn = buildDn(person);
        ldapTemplate.unbind(dn);
    }

    /* 其他 */

    public static final String BASE_DN = "dc=example,dc=com";

    /**
     * 动态创建dn
     */
    private Name buildDn(Person person) {
        return LdapNameBuilder.newInstance(BASE_DN)
                .add("ou", person.getCompany())
                .add("uid", person.getUid())
                .build();
    }

    /**
     * 动态构建属性
     */
    private Attributes buildAttributes(Person person) {
        Attributes attrs = new BasicAttributes();
        try {
            BasicAttribute objectclass = new BasicAttribute("objectclass");
            objectclass.add("top");
            objectclass.add("person");
            objectclass.add("organizationalPerson");
            objectclass.add("inetOrgPerson");
            attrs.put(objectclass);
            //attrID
            attrs.put("ou", person.getCompany());
            attrs.put("uid", person.getUid());
            attrs.put("cn", person.getCommonName());
            attrs.put("sn", person.getSurname());
            attrs.put("description", person.getDescription());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attrs;
    }

}

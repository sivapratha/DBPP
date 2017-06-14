package com.hp.test.dbpowerpack.action;

import servletunit.struts.CactusStrutsTestCase;

public class LoginActionTest extends CactusStrutsTestCase {
 
	public void setUp() throws Exception { super.setUp(); }

    public void tearDown()  throws Exception { super.tearDown(); }

    public LoginActionTest(String testName) { super(testName); }

    public void testSuccessfulLogin() {
    	  setRequestPathInfo("/login.do");
          addRequestParameter("userName","deryl");
          addRequestParameter("passWord","radar");
          actionPerform();
          verifyForward("login");
          //assertEquals("deryl",(String) getSession().getAttribute("authentication"));
    }
}

package com.utn.tacs.tp2016c1g4.marvel_webapp;

public class HeroesTest extends AbstractServerTest {

	public HeroesTest(String testName) {
		super(testName);
	}

	public void testHeroes() {
		String responseMsg = r.path("heroes").get(String.class);
		assertEquals("{}", responseMsg);
	}

}

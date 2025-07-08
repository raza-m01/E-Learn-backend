package com.elearn.app;

import com.elearn.app.services.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StartLearnBackendApplicationTests {


	@Autowired
	private CategoryService categoryService;

	@Test
	void contextLoads() {
	}

	@Test
	public void addingCourseToCategoryTest(){

		categoryService.addCourseCategory("279dcfa3-8b6b-4a28-b00d-ee6b8fde3891","9ef49bd0-cc37-4a27-9003-5e606488cc53");
	}

}

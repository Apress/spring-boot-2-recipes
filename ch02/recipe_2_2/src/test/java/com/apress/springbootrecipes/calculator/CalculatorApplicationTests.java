package com.apress.springbootrecipes.calculator;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CalculatorApplication.class)
public class CalculatorApplicationTests {

	@Rule
	public OutputCapture capture = new OutputCapture();

	@MockBean(name ="division")
	private Operation mockOperation;

	@Autowired
	private Calculator calculator;

	@Test
	public void calculatorShouldHave3Operations() {
		Object operations =
						ReflectionTestUtils.getField(calculator, "operations");

		assertThat((Collection) operations).hasSize(3);
	}

	@Test
	public void mockDivision() {
		when(mockOperation.handles('/')).thenReturn(true);
		when(mockOperation.apply(14, 7)).thenReturn(2);

		calculator.calculate(14,7, '/');
		capture.expect(Matchers.containsString("14 / 7 = 2"));
	}

	@Test
	public void doingMultiplicationShouldSucceed() {
		calculator.calculate(12,13, '*');
		capture.expect(Matchers.containsString("12 * 13 = 156"));
	}

	@Test
	public void doingAdditionShouldSucceed() {
		calculator.calculate(12,13, '+');
		capture.expect(Matchers.containsString("12 + 13 = 25"));
	}


	@Test(expected = IllegalArgumentException.class)
	public void doingDivisionShouldFail() {
		calculator.calculate(12,13, '/');
	}


}

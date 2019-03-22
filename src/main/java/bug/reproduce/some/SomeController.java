package bug.reproduce.some;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SomeController {

	@GetMapping
	public Something hello() {
		Something something = new Something();
		something.setSomeMessage("Hello Bug");
		return something;
	}

	public static class Something {
		private String someMessage;

		/**
		 * Something to describe
		 * @return the thing
		 */
		public String getSomeMessage() {
			return someMessage;
		}

		public void setSomeMessage(String someMessage) {
			this.someMessage = someMessage;
		}
	}
}

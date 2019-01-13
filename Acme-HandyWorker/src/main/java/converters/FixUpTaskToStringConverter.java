package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.FixUpTask;

@Component
@Transactional
public class FixUpTaskToStringConverter implements Converter<FixUpTask, String> {

	@Override
	public String convert(FixUpTask fixuptask) {
		String result;

		if (fixuptask == null) {
			result = null;
		} else {
			result = String.valueOf(fixuptask.getId());
		}
		return result;
	}
}

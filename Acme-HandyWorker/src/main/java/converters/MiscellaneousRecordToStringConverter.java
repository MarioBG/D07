package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.MiscellaneousRecord;

@Component
@Transactional
public class MiscellaneousRecordToStringConverter implements Converter<MiscellaneousRecord, String> {

	@Override
	public String convert(MiscellaneousRecord miscellaneousrecord) {
		String result;

		if (miscellaneousrecord == null) {
			result = null;
		} else {
			result = String.valueOf(miscellaneousrecord.getId());
		}
		return result;
	}
}

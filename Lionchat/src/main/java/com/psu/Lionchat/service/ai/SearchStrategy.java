package com.psu.Lionchat.service.ai;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class SearchStrategy extends IntentStrategyAbs {
	@Override
	// Returns search string from Google
	public String doStrategy(String question) {
		try {
			question = URLEncoder.encode(question, StandardCharsets.UTF_8.toString());
			String search = String.format("http://www.google.com/search?q=%s", question);
			return String.format("<a href=%s target=\"_blank\">%s<" + "/a>", search, search);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "Failed to create google search. (invalid character present?)";
		}
	}
}

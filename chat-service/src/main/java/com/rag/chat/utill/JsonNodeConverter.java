package com.rag.chat.utill;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Converter
public class JsonNodeConverter implements AttributeConverter<JsonNode, String> {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(JsonNode attribute) {
		try {
			return attribute != null ? objectMapper.writeValueAsString(attribute) : null;
		} catch (Exception e) {
			throw new IllegalArgumentException("Error converting JSON", e);
		}
	}
	
    @Override
    public JsonNode convertToEntityAttribute(String dbData) {
        try {
            return dbData != null ? objectMapper.readTree(dbData) : null;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error reading JSON", e);
        }
    }
	
	

}

package com.boco.mis.persist.elasticsearch.index;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.boco.mis.persist.elasticsearch.annotation.EsIndex;
import com.boco.mis.persist.elasticsearch.annotation.EsIndexField;

public class IndexDefine {

	private String indexName;

	private IndexDefineSettings settings;

	private Map<String, IndexDefineType> mappings;

	public IndexDefine() {
		settings = new IndexDefineSettings();
		mappings = new HashMap<String, IndexDefineType>();
	}

	public IndexDefineSettings getSettings() {
		return settings;
	}

	public void setSettings(IndexDefineSettings settings) {
		this.settings = settings;
	}

	public Map<String, IndexDefineType> getMappings() {
		return mappings;
	}

	public void setMappings(Map<String, IndexDefineType> mappings) {
		this.mappings = mappings;
	}

	public void indexName(String indexName) {
		this.indexName = indexName;
	}

	public String indexName() {
		return indexName;
	}

	public static IndexDefine generateIndexDefine(Class<?> modelClazz) {

		IndexDefine define = new IndexDefine();
		Map<String, Object> index = define.getSettings().getIndex();
		index.put("number_of_shards", 6);
		index.put("number_of_replicas", 0);

		EsIndex esIndex = modelClazz.getAnnotation(EsIndex.class);

		if (esIndex == null) {
			return null;
		}
		Map<String, IndexDefineType> mappings = define.getMappings();

		define.indexName(esIndex.indexName());

		String type = esIndex.indexType();
		IndexDefineType defineType = new IndexDefineType();
		mappings.put(type, defineType);

		Map<String, IndexDefineField> properties = defineType.getProperties();
		Field[] fields = modelClazz.getDeclaredFields();
		for (Field field : fields) {
			EsIndexField esIndexField = field.getAnnotation(EsIndexField.class);
			if (esIndexField == null)
				continue;
			String fieldName = esIndexField.fieldName();
			if(fieldName == null || fieldName.length() == 0) {
				fieldName = field.getName();
			}
			String fieldType = esIndexField.fieldType();
			IndexDefineField defineField = new IndexDefineField();
			defineField.setType(fieldType);
			properties.put(fieldName, defineField);
		}

		return define;
	}
	
}

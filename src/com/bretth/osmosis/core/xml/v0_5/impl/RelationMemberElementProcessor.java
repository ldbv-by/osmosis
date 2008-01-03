// License: GPL. Copyright 2007-2008 by Brett Henderson and other contributors.
package com.bretth.osmosis.core.xml.v0_5.impl;

import org.xml.sax.Attributes;

import com.bretth.osmosis.core.domain.v0_5.EntityType;
import com.bretth.osmosis.core.domain.v0_5.RelationMember;
import com.bretth.osmosis.core.xml.common.BaseElementProcessor;


/**
 * Provides an element processor implementation for a relation member.
 * 
 * @author Brett Henderson
 */
public class RelationMemberElementProcessor extends BaseElementProcessor {
	private static final String ATTRIBUTE_NAME_ID = "ref";
	private static final String ATTRIBUTE_NAME_TYPE = "type";
	private static final String ATTRIBUTE_NAME_ROLE = "role";
	
	private RelationMemberListener relationMemberListener;
	private RelationMember relationMember;
	private MemberTypeParser memberTypeParser;
	
	
	/**
	 * Creates a new instance.
	 * 
	 * @param parentProcessor
	 *            The parent element processor.
	 * @param relationMemberListener
	 *            The relation member listener for receiving created tags.
	 */
	public RelationMemberElementProcessor(BaseElementProcessor parentProcessor, RelationMemberListener relationMemberListener) {
		super(parentProcessor, true);
		
		this.relationMemberListener = relationMemberListener;
		
		memberTypeParser = new MemberTypeParser();
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	public void begin(Attributes attributes) {
		long id;
		EntityType type;
		String role;
		
		id = Long.parseLong(attributes.getValue(ATTRIBUTE_NAME_ID));
		type = memberTypeParser.parse(attributes.getValue(ATTRIBUTE_NAME_TYPE));
		role = attributes.getValue(ATTRIBUTE_NAME_ROLE);
		
		relationMember = new RelationMember(id, type, role);
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	public void end() {
		relationMemberListener.processRelationMember(relationMember);
		relationMember = null;
	}
}

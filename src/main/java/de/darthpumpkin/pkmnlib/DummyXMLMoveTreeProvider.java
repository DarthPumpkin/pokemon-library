/**
 * 
 */
package de.darthpumpkin.pkmnlib;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author dominik
 * 
 */
public class DummyXMLMoveTreeProvider implements MoveTreeProvider {

	private static final String FILE_PREFIX;
	private static final String FILE_POSTFIX;
	private static final String PROBABILITY_TAG;
	private static final String EFFECT_ID_TAG;
	private static final String SUBELEMENT_TAG;
	private static final String OCCASION_ATTRIBUTE_NAME;
	private static final String SUCCESS_VALUE;
	private static final String FAILURE_VALUE;

	static {
		FILE_PREFIX = "data/";
		FILE_POSTFIX = ".xml";
		PROBABILITY_TAG = "probability";
		EFFECT_ID_TAG = "effectId";
		SUBELEMENT_TAG = "atomar";
		OCCASION_ATTRIBUTE_NAME = "occasion";
		SUCCESS_VALUE = "success";
		FAILURE_VALUE = "failure";
	}

	private Move move;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.darthpumpkin.pkmnlib.MoveTreeProvider#setMove(de.darthpumpkin.pkmnlib
	 * .Move)
	 */
	@Override
	public void setMove(Move move) {
		this.move = move;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.darthpumpkin.pkmnlib.MoveTreeProvider#attachMoveTree()
	 */
	@Override
	public void attachMoveTree() {
		int moveId = move.getId();
		String filename = FILE_PREFIX + moveId + FILE_POSTFIX;
		try {
			Document doc = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().parse(filename);
			doc.getDocumentElement().normalize();
			NodeList roots = doc.getElementsByTagName(SUBELEMENT_TAG);
			for (int i = 0; i < roots.getLength(); i++) {
				Element root = (Element) roots.item(i);
				AtomarMove rootAtomarMove = makeAtomarMove(root);
				String occasion = root.getAttribute(OCCASION_ATTRIBUTE_NAME);
				if (occasion.equals(SUCCESS_VALUE)) {
					move.setSuccessElement(rootAtomarMove);
				} else if (occasion.equals(FAILURE_VALUE)) {
					move.setFailureElement(rootAtomarMove);
				} else {
					throw new RuntimeException("Malformed XML: " + occasion
							+ " is not a valid occasion");
				}
			}
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Turn the {@link Element} representation of an AtomarMove recursively into
	 * an actual {@link AtomarMove} object.
	 * 
	 * @param doc
	 *            the {@link Element} representing the AtomarMove
	 * @return the AtomarMove
	 */
	private AtomarMove makeAtomarMove(Element doc) {
		NodeList probability = doc.getElementsByTagName(PROBABILITY_TAG);
		NodeList effectId = doc.getElementsByTagName(EFFECT_ID_TAG);
		NodeList subelements = doc.getElementsByTagName(SUBELEMENT_TAG);
		/*
		 * Exception handling
		 */
		if (probability.getLength() != 1) {
			throw new RuntimeException("Malformed XML: "
					+ probability.getLength() + " probabilities");
		}
		if (effectId.getLength() != 1) {
			throw new RuntimeException("Malformed XML: " + effectId.getLength()
					+ " effectIds");
		}
		if (subelements.getLength() > 2) {
			throw new RuntimeException("Malformed XML: "
					+ subelements.getLength() + " atomars");
		}
		AtomarMove am = new AtomarMove();
		/*
		 * now start
		 */
		am.setEffectId(Integer.parseInt(effectId.item(0).getTextContent()));
		am.setProbability(Integer
				.parseInt(probability.item(0).getTextContent()));
		for (int i = 0; i < subelements.getLength(); i++) {
			Element subelement = (Element) subelements.item(i);
			String occasion = subelement.getAttribute(OCCASION_ATTRIBUTE_NAME);
			if (occasion.equals(SUCCESS_VALUE)) {
				/*
				 * recursion
				 */
				am.setSuccessElement(makeAtomarMove(subelement));
			} else if (occasion.equals(FAILURE_VALUE)) {
				/*
				 * recursion
				 */
				am.setFailureElement(makeAtomarMove(subelement));
			} else {
				throw new RuntimeException("Malformed XML: " + occasion
						+ " is not a valid occasion");
			}
		}
		return am;
	}

}

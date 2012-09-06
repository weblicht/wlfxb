package de.tuebingen.uni.sfs.wlf1.tc.xb;

import de.tuebingen.uni.sfs.wlf1.tc.api.MatchedItem;
import de.tuebingen.uni.sfs.wlf1.utils.CommonAttributes;
import java.util.*;
import javax.xml.bind.annotation.*;

/**
 * @author Yana Panchenko
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = MatchedItemStored.XML_NAME)
@XmlType(propOrder = {"targets", "categories", "srcIds", "tokIds"})
public class MatchedItemStored implements MatchedItem {

    public static final String XML_NAME = "item";
    public static final String XML_ATTRIBUTE_SOURCE_IDs = "srcIDs";
    public static final String XML_ELEMENT_TARGET = "target";
    public static final String XML_ELEMENT_CATEGORY = "category";
    @XmlAttribute(name = CommonAttributes.CONSECUTIVE_TOKENS_REFERENCE, required = true)
    String[] tokIds;
    @XmlAttribute(name = XML_ATTRIBUTE_SOURCE_IDs, required = true)
    String[] srcIds;
    @XmlElement(name = XML_ELEMENT_TARGET)
    List<MatchedItemTarget> targets = new ArrayList<MatchedItemTarget>();
    @XmlElement(name = XML_ELEMENT_CATEGORY)
    List<MatchedItemCategory> categories = new ArrayList<MatchedItemCategory>();

    MatchedItemStored() {
    }

    MatchedItemStored(String[] tokIds, String[] srcIds, Map<String, String> targetsMap, Map<String, String> categoriesMap) {
        this.tokIds = tokIds;
        this.srcIds = srcIds;
        for (String name : targetsMap.keySet()) {
            targets.add(new MatchedItemTarget(name, targetsMap.get(name)));
        }
        for (String name : categoriesMap.keySet()) {
            categories.add(new MatchedItemCategory(name, categoriesMap.get(name)));
        }
    }

    @Override
    public String[] getOriginCorpusTokenIds() {
        return srcIds;
    }

    @Override
    public Set<String> getTargetNames() {
        Set<String> names = new HashSet<String>();
        for (MatchedItemTarget target : this.targets) {
            names.add(target.name);
        }
        return names;
    }

    @Override
    public String getTargetValue(String targetName) {
        for (MatchedItemTarget target : this.targets) {
            if (targetName.equals(target.name)) {
                return target.value;
            }
        }
        return null;
    }

    @Override
    public Set<String> getCategoriesNames() {
        Set<String> names = new HashSet<String>();
        for (MatchedItemCategory cat : this.categories) {
            names.add(cat.name);
        }
        return names;
    }

    @Override
    public String getCategoryValue(String categoryName) {
        for (MatchedItemCategory cat : this.categories) {
            if (categoryName.equals(cat.name)) {
                return cat.value;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Arrays.toString(tokIds));
        sb.append(" ");
        sb.append(Arrays.toString(srcIds));
        if (!targets.isEmpty()) {
            sb.append(" ");
            sb.append(targets);
        }
        if (!categories.isEmpty()) {
            sb.append(" ");
            sb.append(categories);
        }
        return sb.toString();
    }
}

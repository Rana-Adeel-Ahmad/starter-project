package com.ncms.module.utils

import com.ncms.module.models.maps.mapservices.DimensionData
import com.ncms.module.models.maps.mapservices.DimensionItem
import com.ncms.module.models.maps.mapservices.EXGeographicBoundingBox
import com.ncms.module.models.maps.mapservices.LayerItem
import com.ncms.module.models.maps.mapservices.LayerLegendURL
import com.ncms.module.models.maps.mapservices.LayerStyle
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList

object NcmXmlParser {

    fun parseMapTilesResponse(xmlString: String): ArrayList<LayerItem> {
        val layerData: ArrayList<LayerItem> = ArrayList()
        var parser = XMLParser()
        val doc: Document = parser.getDomElement(xmlString)
        val dimNodes: NodeList = doc.getElementsByTagName("Dimension")
        for (i in 0 until dimNodes.length) {
            val dimNode: Element = dimNodes.item(i) as Element
            val pNode = dimNode.parentNode as Element
            if (pNode.tagName.toLowerCase() != "layer") {
                continue
            }
            var title = getValue(parser, pNode, "Title")
            var name = getValue(parser, pNode, "Name")
            var exGeographicBoundingBox = EXGeographicBoundingBox()
            var dimensions: ArrayList<DimensionItem> = ArrayList()
            var layerStyle = LayerStyle()
            try {
                val childNodes = pNode.childNodes
                for (i in 0 until childNodes.length) {
                    if (childNodes.item(i).getNodeType() == Element.ELEMENT_NODE) {
                        val child = childNodes.item(i) as Element
                        if (child.tagName.equals("EX_GeographicBoundingBox", true)) {
                            val exBoundingElement = child
                            exGeographicBoundingBox.westBoundLongitude =
                                getValue(parser, exBoundingElement, "westBoundLongitude")
                            exGeographicBoundingBox.eastBoundLongitude =
                                getValue(parser, exBoundingElement, "eastBoundLongitude")
                            exGeographicBoundingBox.southBoundLatitude =
                                getValue(parser, exBoundingElement, "southBoundLatitude")
                            exGeographicBoundingBox.northBoundLatitude =
                                getValue(parser, exBoundingElement, "northBoundLatitude")
                        } else if (child.tagName.equals("Dimension", true)) {
                            var dimensionName = getAttributeNode(child, "name")
                            var default = getAttributeNode(child, "default")
                            var contents = child.textContent
                            dimensions.add(
                                DimensionItem(
                                    dimensionName,
                                    default,
                                    ArrayList(contents.split(","))
                                )
                            )
                        } else if (child.tagName.equals("Style", true)) {
                            val styleElement = child
                            var format = ""
                            var href = ""
                            layerStyle.name =
                                getValue(parser, styleElement, "Name")
                            layerStyle.title =
                                getValue(parser, styleElement, "Title")
                            try {
                                val legendURLElemet = child.getElementsByTagName("LegendURL")
                                for (k in 0 until legendURLElemet.getLength()) {
                                    val n: Node = legendURLElemet.item(k)
                                    val nChilds = n.childNodes
                                    for (l in 0 until nChilds.length) {
                                        if (nChilds.item(l).getNodeType() == Element.ELEMENT_NODE) {
                                            val nChild = nChilds.item(l) as Element
                                            if (nChild.tagName.equals(
                                                    "Format",
                                                    true
                                                )
                                            ) {
                                                format = nChild.textContent
                                            } else if (nChild.tagName.equals(
                                                    "OnlineResource",
                                                    true
                                                )
                                            ) {
                                                href = getAttributeNode(nChild, "xlink:href")
                                                val layerLegendURL = LayerLegendURL(format, href)
                                                layerStyle.legendURL = layerLegendURL
                                            }
                                        }
                                    }
                                }

                            } catch (ex: Exception) {
                                val err = ex.message
                            }
                        }
                    }
                }
            } catch (ex: Exception) {
                val e = ex
            }
            val dimensionData = DimensionData(exGeographicBoundingBox, dimensions, layerStyle)
            layerData.add(LayerItem(name, title, dimensionData))
        }
        return ArrayList(layerData.distinctBy { it.name })
    }


    private fun getValue(parser: XMLParser, element: Element, key: String): String {
        try {
            return parser.getValue(element, key)
        } catch (ex: Exception) {
            return ""
        }
    }

    private fun getAttributeNode(element: Element, name: String): String {
        return try {
            element.getAttributeNode(name).value
        } catch (ex: Exception) {
            ""
        }
    }


}
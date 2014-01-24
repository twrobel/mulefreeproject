<?xml version="1.0" encoding="utf-8" ?>
<xsl:stylesheet version="2.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
  <spellrequest textalreadyclipped="0" ignoredups="0" ignoredigits="1" ignoreallcaps="1">
  <text>
    <xsl:value-of select="map/entry/string[2]" />
  </text>
  </spellrequest>
</xsl:template>


</xsl:stylesheet>
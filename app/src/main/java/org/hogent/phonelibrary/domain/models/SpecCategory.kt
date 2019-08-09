package org.hogent.phonelibrary.domain.models

data class SpecCategory(val identifier : SpecCategoryEnum, val specs: List<IDeviceSpec>)
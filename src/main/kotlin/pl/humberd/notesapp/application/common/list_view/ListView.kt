package pl.humberd.notesapp.application.common.list_view

open class ListView<DataType, Extra : ListViewExtra>(
    var data: List<DataType>,
    var extra: Extra
)


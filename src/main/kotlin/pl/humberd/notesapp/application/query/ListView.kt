package pl.humberd.notesapp.application.query

open class ListView<DataType, Extra : ListViewExtra>(
    var data: List<DataType>,
    var extra: Extra
)


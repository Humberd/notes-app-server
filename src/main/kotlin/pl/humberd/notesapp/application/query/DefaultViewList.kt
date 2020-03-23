package pl.humberd.notesapp.application.query

class DefaultViewList<DataType>(
    data: List<DataType>,
    extra: ListViewExtra
) : ListView<DataType, ListViewExtra>(data, extra)

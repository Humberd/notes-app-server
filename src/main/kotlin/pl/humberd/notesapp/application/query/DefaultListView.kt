package pl.humberd.notesapp.application.query

class DefaultListView<DataType>(
    data: List<DataType>,
    extra: ListViewExtra
) : ListView<DataType, ListViewExtra>(data, extra)

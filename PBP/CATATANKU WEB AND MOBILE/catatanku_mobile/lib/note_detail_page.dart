
import 'package:flutter/material.dart';
import 'package:catatanku_mobile/notes.dart';


class NoteDetailPage extends StatefulWidget {
  const NoteDetailPage({Key? key}) : super(key: key);

  @override
  _NoteDetailState createState() => _NoteDetailState();
}

class _NoteDetailState extends State<NoteDetailPage> {
  @override
  Widget build(BuildContext context) {
    final dynamic arguments = ModalRoute.of(context)!.settings.arguments;
    final Notes notes = arguments as Notes;
    final String title = notes.fields.title;
    final String description = notes.fields.description;

    return Scaffold(
      appBar: AppBar(
        title: Text("$title"),
        centerTitle: true,
        foregroundColor: Colors.white,
        backgroundColor: Colors.deepOrange,
      ),
      body: Container(
        padding: EdgeInsets.fromLTRB(5, 5, 5, 75),
        child: SingleChildScrollView(
            padding: EdgeInsets.symmetric(vertical: 10.0, horizontal: 20.0),
            child: Text(description),
          ),
      ),
      floatingActionButton: FloatingActionButton(
        foregroundColor: Colors.white,
        backgroundColor: Colors.deepOrange[900],
        onPressed: () async {
          Navigator.pushNamed(
              context,
              '/edit_note_page',
              arguments:notes,
          );
        },
        child: Icon(Icons.edit),
      ),
    );
  }
}
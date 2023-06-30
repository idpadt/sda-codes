// ignore_for_file: prefer_const_constructors

import 'package:catatanku_mobile/note.dart';
import 'package:catatanku_mobile/notes.dart';
import 'package:flutter/material.dart';

class AddNote extends StatelessWidget {
  AddNote({super.key, required this.function});

  final Function(String, String) function;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('New Note'),
        backgroundColor: Colors.green[200],
      ),
      body: Center(
        child: Container(
          margin: EdgeInsets.symmetric(horizontal: 20.0, vertical: 10.0),
          child: NoteForm(function: function,),
        ),
      )
    );
  }
}

class NoteForm extends StatefulWidget {
  const NoteForm({super.key, required this.function});

  final Function(String, String) function;

  @override
  State<NoteForm> createState() => _NoteFormState();
}

class _NoteFormState extends State<NoteForm> {

  final _formKey = GlobalKey<FormState>();
  late String _title;
  late String _description;

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisAlignment: MainAxisAlignment.start,
      crossAxisAlignment: CrossAxisAlignment.center,
      children: [
        Form(
          key: _formKey,
          child:Column(
            children: [
              TextFormField(
                maxLines: null,
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Empty?';
                  }
                  return null;
                },
                decoration: InputDecoration(
                  border: UnderlineInputBorder(),
                  hintText: "Title"
                ),
                onSaved: (String? text) {
                  setState(() {
                    _title = text!;
                  });
                },
              ),
              SizedBox(
                height: 20.0,
              ),
              TextFormField(
                maxLines: null,
                validator: (value) {
                  if (value == null || value.isEmpty) {
                    return 'Not even a single character?';
                  }
                  return null;
                },
                decoration: InputDecoration(
                  border: UnderlineInputBorder(),
                  hintText: "Description"
                ),
                onSaved: (String? text) {
                  setState(() {
                    _description = text!;
                  });
                },
              ),
              SizedBox(
                height: 20.0,
              ),
              ElevatedButton(
                onPressed: () {
                  if (_formKey.currentState!.validate()) {
                    _formKey.currentState!.save();
                    widget.function(_title, _description);
                    Navigator.pop(context);
                  }
                },
                child: const Text('Submit'),
              ),
            ],
          ),
        ),
      ],
    );
  }
}
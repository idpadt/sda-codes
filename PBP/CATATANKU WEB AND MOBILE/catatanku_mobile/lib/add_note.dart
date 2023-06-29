// ignore_for_file: prefer_const_constructors

import 'package:catatanku_mobile/note.dart';
import 'package:flutter/material.dart';

class AddNote extends StatelessWidget {
  const AddNote({super.key});

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
          child: NoteForm(),
        ),
      )
    );
  }
}

class NoteForm extends StatefulWidget {
  const NoteForm({super.key});

  @override
  State<NoteForm> createState() => _NoteFormState();
}

class _NoteFormState extends State<NoteForm> {

  final _formKey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisAlignment: MainAxisAlignment.start,
      crossAxisAlignment: CrossAxisAlignment.center,
      children: [
        Form(
          key: _formKey,
          child:TextFormField(
            maxLines: 2,
            validator: (value) {
              if (value == null || value.isEmpty) {
                return 'Please enter some text';
              }
              return null;
            },
            decoration: InputDecoration(
              border: UnderlineInputBorder(),
              labelText: 'Title',
              labelStyle: TextStyle(
                fontSize: 20.0,
              ),
            ),
          ),
        ),
      ],
    );
  }
}
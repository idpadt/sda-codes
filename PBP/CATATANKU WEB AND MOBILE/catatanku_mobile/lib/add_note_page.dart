// ignore_for_file: prefer_const_constructors, use_build_context_synchronously

import 'package:catatanku_mobile/main.dart';
import 'package:catatanku_mobile/notes.dart';
import 'package:flutter/material.dart';
import 'dart:convert' as convert;
import 'package:http/http.dart' as http;

class AddNotePage extends StatelessWidget {
  AddNotePage({super.key, required this.function});

  final Function() function;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        foregroundColor: Colors.white,
        backgroundColor: Colors.deepOrange,
        title: const Text('New Note'),
        centerTitle: true,
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

  final Function() function;

  @override
  State<NoteForm> createState() => _NoteFormState();
}

class _NoteFormState extends State<NoteForm> {

  final _formKey = GlobalKey<FormState>();
  late String _title;
  late String _description;

  Future<http.Response> createNote(String title, String description) {
    return http.post(
      Uri.parse('http://localhost:8000/create_flutter/'),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
      },
      body: convert.jsonEncode(<String, String>{
        'title': title,
        'description': description,
      }),
    );
  }

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      padding: EdgeInsets.fromLTRB(5, 5, 5, 30),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.start,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Form(
              key: _formKey,
              child:Column(
                mainAxisAlignment: MainAxisAlignment.start,
                crossAxisAlignment: CrossAxisAlignment.center,
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
                      labelText: "Title"
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
                      labelText: "Description"
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
                    style: ButtonStyle(
                      backgroundColor: MaterialStateProperty.all<Color?>(Colors.deepOrange[900]),
                      foregroundColor: MaterialStateProperty.all<Color>(Colors.white),
                    ),
                    onPressed: () async {
                      if (_formKey.currentState!.validate()) {
                        _formKey.currentState!.save();
                        final response = await createNote(_title, _description);
                        if (response.statusCode == 200) {
                          final responseData = convert.jsonDecode(response.body);
                          if (responseData['status'] == 'success') {
                            ScaffoldMessenger.of(context).showSnackBar(
                              const SnackBar(
                                content: Text("You added a new note! :) "),
                              ),
                            );
                            widget.function();
                            Navigator.pop(context);
                          } else {
                            ScaffoldMessenger.of(context).showSnackBar(
                              const SnackBar(
                                content: Text("Failed to add note. :( "),
                              ),
                            );
                          }
                        } else {
                          ScaffoldMessenger.of(context).showSnackBar(
                            const SnackBar(
                              content: Text("Something went wrong. Please try again."),
                            ),
                          );
                        }
                      }
                    },
                    child: const Text('Done'),
                  ),
                ],
              ),
            ),
        ],
      ),
    );
  }
}

// ignore_for_file: prefer_const_constructors

import 'package:catatanku_mobile/main.dart';
import 'package:catatanku_mobile/note_detail_page.dart';
import 'package:flutter/material.dart';
import 'package:catatanku_mobile/notes.dart';
import 'dart:convert' as convert;
import 'package:http/http.dart' as http;

class EditNotePage extends StatefulWidget {
  const EditNotePage({Key? key}) : super(key: key);

  @override
  State<EditNotePage> createState() => _EditNotePageState();
}

class _EditNotePageState extends State<EditNotePage> {
  @override
  Widget build(BuildContext context) {

    final dynamic arguments = ModalRoute.of(context)!.settings.arguments;
    final Notes notes = arguments as Notes;
    late String oldTitle = notes.fields.title;
    final int id = notes.pk;
    final _formKey = GlobalKey<FormState>();
    late String newTitle;
    late String newDescription;

    Future<http.Response> editNote(int id, String title, String description) {
      return http.put(
        Uri.parse('http://localhost:8000/edit_flutter/$id/'),
        headers: <String, String>{
          'Content-Type': 'application/json; charset=UTF-8',
        },
        body: convert.jsonEncode(<String, String>{
          'title': title,
          'description': description,
        }),
      );
    }

    return Scaffold(
      appBar: AppBar(
        title: Text("Edit Note"),
        centerTitle: true,
        foregroundColor: Colors.white,
        backgroundColor: Colors.deepOrange,
      ),
      body:SingleChildScrollView(
        padding: EdgeInsets.fromLTRB(5, 5, 5, 30),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.start,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            Form(
              key: _formKey,
              child:Column(
                children: [
                  TextFormField(
                    initialValue: notes.fields.title,
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
                        newTitle = text!;
                      });
                    },
                  ),
                  SizedBox(
                    height: 20.0,
                  ),
                  TextFormField(
                    initialValue: notes.fields.description,
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
                        newDescription = text!;
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
                        final response = await editNote(id, newTitle, newDescription);
                        if (response.statusCode == 200) {
                          final responseData = convert.jsonDecode(response.body);
                          if (responseData['status'] == 'success') {
                            ScaffoldMessenger.of(context).showSnackBar(
                              const SnackBar(
                                content: Text("You edited a note!"),
                              ),
                            );
                            // widget.function();
                            Navigator.pop(context, notes); // Navigate back to the previous page
                          } else {
                            ScaffoldMessenger.of(context).showSnackBar(
                              const SnackBar(
                                content: Text("Failed to edit note."),
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
      )
    );
  }
}
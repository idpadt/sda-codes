// ignore_for_file: prefer_const_constructors, prefer_const_literals_to_create_immutables

import 'package:flutter/material.dart';
import 'note.dart';

void main() {
  runApp(const Catatanku());
}

class Catatanku extends StatelessWidget {
  const Catatanku({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: ThemeData(
        useMaterial3: true,
      ),
      home: MainPage(),
    );
  }
}

class MainPage extends StatefulWidget {
  const MainPage({super.key});

  @override
  State<MainPage> createState() => _MainPageState();
}

class _MainPageState extends State<MainPage> {

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      floatingActionButton: FloatingActionButton(
        onPressed: (){},
        child: Icon(Icons.add), 
      ),
      appBar: AppBar(
        title: Text("Catatanku"),
        centerTitle: true,
      ),
      body: Center(
        child: ListView.builder(
          padding: const EdgeInsets.all(8),
          itemCount: templateNotes.length,
          itemBuilder: (BuildContext context, int index) {
            return NoteCard(note: templateNotes[index]);
          },
          
          //separatorBuilder: (BuildContext context, int index) => const Divider(),
        ),
      ), 
    );
  }
}

List<Note> templateNotes = [Note(title: "sfgsdfgsdgfsdgsdfgdsgfsfgsdfgsdgfsdgsdfgdsgfsfgsdfgsdgfsdgsdfgdsg fsfgsdfgsdgfsdgsdfgdsgf", description: "DETAILSSSS"),
                            Note(title: "TEST 2", description: "DETAILSSSS"),
                            Note(title: "TEST 3", description: "DETAILSSSS"),
                            Note(title: "TEST 4", description: "DETAILSSSS"),
                            Note(title: "TEST 5", description: "DETAILSSSS"),
                            Note(title: "TEST 6", description: "DETAILSSSS"),
                            Note(title: "TEST 7", description: "DETAILSSSS"),
                            Note(title: "TEST 8", description: "DETAILSSSS"),
                            Note(title: "TEST 9", description: "DETAILSSSS"),
                            Note(title: "TEST 10", description: "DETAILSSSS"),
                            Note(title: "TEST 11", description: "DETAILSSSS"),
                            Note(title: "TEST 12", description: "DETAILSSSS"),
                            Note(title: "TEST 13", description: "DETAILSSSS"),
                            Note(title: "TEST 14", description: "DETAILSSSS"),
                            Note(title: "TEST 15", description: "DETAILSSSS"),
                            Note(title: "TEST 16", description: "DETAILSSSS"),
                            Note(title: "TEST 17", description: "DETAILSSSS"),
                            Note(title: "TEST 18", description: "DETAILSSSS"),
                            Note(title: "TEST 19", description: "DETAILSSSS"),
                            Note(title: "BOTTOM", description: "DETAILSSSS"),
                            ];

List<Note> listOfNotes = [];

class NoteCard extends StatefulWidget {
  NoteCard({super.key, required this.note});

  Note note;

  @override
  State<NoteCard> createState() => _NoteCardState();
}

class _NoteCardState extends State<NoteCard> {

  @override
  Widget build(BuildContext context) {

    String? title = widget.note.title;
    String? description = widget.note.description;

    return Center(
      child: Card(
        // clipBehavior is necessary because, without it, the InkWell's animation
        // will extend beyond the rounded edges of the [Card] (see https://github.com/flutter/flutter/issues/109776)
        // This comes with a small performance cost, and you should not set [clipBehavior]
        // unless you need it.
        clipBehavior: Clip.hardEdge,
        child: InkWell(
          splashColor: Colors.blue.withAlpha(30),
          onTap: () {},
          child: SizedBox(
            //width: 300,
            //height: 50,
            child: Center(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Container(
                    padding: EdgeInsets.fromLTRB(15.0, 10.0, 15.0, 5.0),
                    child:(Text('$title')
                    ),
                  ),
                  Container(
                    padding: EdgeInsets.fromLTRB(15.0, 7.5, 15.0, 7.5),
                    alignment: Alignment.bottomRight,
                    child:(Text('Date n Time')),
                  ),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}
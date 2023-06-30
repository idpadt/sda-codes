// ignore_for_file: prefer_const_constructors, prefer_const_literals_to_create_immutables

import 'package:flutter/material.dart';
import 'package:animations/animations.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
//import 'note.dart';
import 'add_note.dart';
import 'note_detail.dart';
import 'notes.dart';

void main() {
  runApp(const Catatanku());
}

class Catatanku extends StatelessWidget {
  const Catatanku({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title:"Catatanku",
      theme: ThemeData(
        useMaterial3: true,
      ),
      /*
      initialRoute: '/',
      routes: {
        '/': (context) => const MainPage(),
        '/add_note': (context) => const AddNote(),
        '/note_detail': (context) => const NoteDetail(),
      },
      */
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

/*   

  Future<List<Notes>> fetchNotes() async {
    var url = Uri.parse(
        'https://123.456.789.000:8000/json/');
    var response = await http.get(
        url,
        headers: {"Content-Type": "application/json"},
    );

    // melakukan decode response menjadi bentuk json
    var data = jsonDecode(utf8.decode(response.bodyBytes));

    // melakukan konversi data json menjadi object TransactionRecord
    List<Notes> listOfNotes = [];
    for (var d in data) {
        if (d != null) {
            listOfNotes.add(Notes.fromJson(d));
        }
    }
    return listOfNotes;
  }

*/
  List<Notes> tempList = [Notes(model: 'catatanku.notes', pk:1, fields: Fields(title: 'TITLE 1', description: 'TESTTESTTESTTEST', date: DateTime.now(), time: '00:00')),
                          Notes(model: 'catatanku.notes', pk:2, fields: Fields(title: 'TITLE 2', description: 'TESTTESTTESTTEST', date: DateTime.now(), time: '00:00')),
                          Notes(model: 'catatanku.notes', pk:3, fields: Fields(title: 'TITLE 3', description: 'TESTTESTTESTTEST', date: DateTime.now(), time: '00:00')),
                          Notes(model: 'catatanku.notes', pk:4, fields: Fields(title: 'TITLE 4', description: 'TESTTESTTESTTEST', date: DateTime.now(), time: '00:00')),];

  void addToList(String title, String description){
    setState(() {
      int newId = tempList[tempList.length-1].pk + 1;
      Notes newNote = Notes(model:'catatanku.notes', pk: newId, fields: Fields(title: title, description: description, date: DateTime.now(), time: '00:00'));
      tempList.add(newNote);
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      floatingActionButton: FloatingActionButton(
        onPressed: (){
          Navigator.push(
            context,
            MaterialPageRoute(
              builder: (context) => AddNote(function: addToList),
            )
          );
        },
        child: Icon(Icons.add), 
      ),
      appBar: AppBar(
        title: Text("Catatanku"),
        centerTitle: true,
      ),
      body: Center(
        child: ListView.separated(
          padding: const EdgeInsets.all(8),
          itemCount: tempList.length,
          itemBuilder: (BuildContext context, int index) {
            return NoteCard(
              notes: tempList[index],
              onTap: (){
                Navigator.pushNamed(context, '/note_detail');
              },
            );
          },
          separatorBuilder: (BuildContext context, int index) => const Divider(height: 0,),
        ),
      ),
/*       FutureBuilder(
        future: fetchNotes(),
        builder: (context, AsyncSnapshot snapshot) {
                if (snapshot.data == null) {
                    return const Center(child: CircularProgressIndicator());
                } else {
                    if (!snapshot.hasData) {
                      return Column(
                          children:  [
                          Text(
                              "No notes found.",
                              style:
                                  TextStyle(color: Color(0xff59A5D8), fontSize: 20),
                          ),
                          SizedBox(height: 8),
                          ],
                      );
                    } else {
                        return ListView.separated(
                          separatorBuilder: (BuildContext context, int index) => const Divider(height: 0,),
                          itemCount: snapshot.data!.length,
                          itemBuilder: (_, index){
                            return NoteCard(
                              notes: snapshot.data![index],
                              onTap: (){
                                Navigator.pushNamed(context, '/note_detail');
                              },
                            );
                          }
                        );
                    }
                }
            },
      ) */
    );
  }
}
/*
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
*/

class NoteCard extends StatefulWidget {
  NoteCard({super.key, required this.notes, required this.onTap});

  Notes notes;
  final GestureTapCallback? onTap;

  @override
  State<NoteCard> createState() => _NoteCardState();
}

class _NoteCardState extends State<NoteCard> {

  @override
  Widget build(BuildContext context) {

    String? title = widget.notes.fields.title;
    String? description = widget.notes.fields.description;
    DateTime? date = widget.notes.fields.date;
    var year = date.year.toString();
    var month = date.month.toString();
    var day = date.day.toString();
    String? time = widget.notes.fields.time;
    //final GestureTapCallback? onTap;

    /* return Center(
      child: Card(
        // clipBehavior is necessary because, without it, the InkWell's animation
        // will extend beyond the rounded edges of the [Card] (see https://github.com/flutter/flutter/issues/109776)
        // This comes with a small performance cost, and you should not set [clipBehavior]
        // unless you need it.
        clipBehavior: Clip.hardEdge,
        child: InkWell(
          splashColor: Colors.blue.withAlpha(30),
          onTap: widget.onTap,
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
    ); */
    return ListTile(
      title:Text(title),
      subtitle: Text("$year-$month-$day -- $time"),
      enabled: true,
      onTap: widget.onTap,
    );
  }
}
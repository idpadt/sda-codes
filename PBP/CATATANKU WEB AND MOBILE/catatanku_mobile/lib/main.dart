// ignore_for_file: prefer_const_constructors, prefer_const_literals_to_create_immutables

import 'package:flutter/material.dart';
import 'package:animations/animations.dart';
import 'package:http/http.dart' as http;
import 'dart:convert' as convert;
import 'add_note_page.dart';
import 'note_detail_page.dart';
import 'edit_note_page.dart';
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
      
      initialRoute: '/',
      routes: {
        '/': (context) => const MainPage(),
        //'/add_note': (context) => const AddNotePage(),
        '/note_detail_page': (context) => const NoteDetailPage(),
        '/edit_note_page' :(context) => const EditNotePage(),
      },
      
      //home: MainPage(),
    );
  }
}

class MainPage extends StatefulWidget {
  const MainPage({super.key});

  @override
  State<MainPage> createState() => _MainPageState();
}

class _MainPageState extends State<MainPage> {

  Future<List<Notes>> fetchNotes() async {
    var url = Uri.parse(
        'http://localhost:8000/json/');
    var response = await http.get(
        url,
        headers: {
          "Content-Type": "application/json",
          "Access-Control-Allow-Origin": "*",
        },
    );

    var data = convert.jsonDecode(convert.utf8.decode(response.bodyBytes));

    List<Notes> listOfNotes = [];
    for (var d in data) {
        if (d != null) {
            listOfNotes.add(Notes.fromJson(d));
        }
    }
    return listOfNotes;
  }

  void refreshMainPage(){
    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      floatingActionButton: FloatingActionButton(
        foregroundColor: Colors.white,
        backgroundColor: Colors.deepOrange[900],
        onPressed: (){
          Navigator.push(
            context,
            MaterialPageRoute(
              builder: (context) => AddNotePage(function: refreshMainPage),
            )
          );
        },
        child: Icon(Icons.add), 
      ),
      appBar: AppBar(
        title: Text("Catatanku"),
        centerTitle: true,
        foregroundColor: Colors.white,
        backgroundColor: Colors.deepOrange,
      ),
      body: FutureBuilder(
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
                                function: refreshMainPage,
                              );
                            }
                          );
                      }
                  }
              },
        )
    );
  }
}

class NoteCard extends StatefulWidget {
  NoteCard({super.key, required this.notes, required this.function});

  final  Notes notes;
  final Function() function;

  @override
  State<NoteCard> createState() => _NoteCardState();
}

class _NoteCardState extends State<NoteCard> {

  Future<http.Response> deleteNote(int id) {
    return http.delete(Uri.parse('http://localhost:8000/delete_flutter/$id/'));
  }


  @override
  Widget build(BuildContext context) {

    String? title = widget.notes.fields.title;
    String? description = widget.notes.fields.description;
    int? id = widget.notes.pk;
    DateTime? date = widget.notes.fields.date;
    var year = date.year.toString();
    var month = date.month.toString();
    var day = date.day.toString();
    String? time = widget.notes.fields.time.substring(0, 5);

    return Builder( 
      builder: (context) => Material(
        child: ListTile(
          title:Text(title),
          subtitle: Text("$day - $month - $year • $time"),
          enabled: true,
          onTap:() {
            Navigator.pushNamed(
              context,
              '/note_detail_page',
              arguments: widget.notes,
            );
          },
          trailing: GestureDetector(
            behavior: HitTestBehavior.translucent,
            onTap: () async {
              final response = await deleteNote(id);
                if (response.statusCode == 200) {
                  final responseData = convert.jsonDecode(response.body);
                  if (responseData['status'] == 'success') {
                    ScaffoldMessenger.of(context).showSnackBar(
                      SnackBar(content: Text("Deleted $title")),
                    );
                    widget.function();
                  } else {
                    ScaffoldMessenger.of(context).showSnackBar(
                      SnackBar(content: Text("Something went wrong")),
                    );
                  }
                };
            },
            child: Container(
              width: 48,
              height: 48,
              padding: const EdgeInsets.symmetric(vertical: 4.0),
              alignment: Alignment.center,
              child: Icon(Icons.delete, color: Colors.red),
            ),
          )
        ),
      ),
    );
  }
}


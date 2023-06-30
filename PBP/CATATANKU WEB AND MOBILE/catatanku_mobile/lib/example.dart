
// ignore_for_file: prefer_const_literals_to_create_immutables, prefer_const_constructors

import 'package:flutter/material.dart';

class EditableTextWidget extends StatefulWidget {
  @override
  _EditableTextWidgetState createState() => _EditableTextWidgetState();
}

class _EditableTextWidgetState extends State<EditableTextWidget> {
  bool isEditingName = false;
  bool isEditingAge = false;
  String name = "John Doe";
  int age = 25;
  late TextEditingController _nameEditingController;
  late TextEditingController _ageEditingController;

  @override
  void initState() {
    super.initState();
    _nameEditingController = TextEditingController(text: name);
    _ageEditingController = TextEditingController(text: age.toString());
  }

  @override
  void dispose() {
    _nameEditingController.dispose();
    _ageEditingController.dispose();
    super.dispose();
  }

  void startEditingName() {
    setState(() {
      isEditingName = true;
    });
  }

  void submitName() {
    setState(() {
      name = _nameEditingController.text;
      isEditingName = false;
    });
  }

  void startEditingAge() {
    setState(() {
      isEditingAge = true;
    });
  }

  void submitAge() {
    setState(() {
      age = int.parse(_ageEditingController.text);
      isEditingAge = false;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        isEditingName ? buildNameTextField() : buildNameText(),
        isEditingAge ? buildAgeTextField() : buildAgeText(),
      ],
    );
  }

  Widget buildNameText() {
    return Row(
      children: [
        Expanded(child: Text("Name: $name")),
        IconButton(
          icon: Icon(Icons.edit),
          onPressed: startEditingName,
        ),
      ],
    );
  }

  Widget buildNameTextField() {
    return Row(
      children: [
        Expanded(
          child: TextField(
            controller: _nameEditingController,
          ),
        ),
        IconButton(
          icon: Icon(Icons.done),
          onPressed: submitName,
        ),
      ],
    );
  }

  Widget buildAgeText() {
    return Row(
      children: [
        Expanded(child: Text("Age: $age")),
        IconButton(
          icon: Icon(Icons.edit),
          onPressed: startEditingAge,
        ),
      ],
    );
  }

  Widget buildAgeTextField() {
    return Row(
      children: [
        Expanded(
          child: TextField(
            controller: _ageEditingController,
            keyboardType: TextInputType.number,
          ),
        ),
        IconButton(
          icon: Icon(Icons.done),
          onPressed: submitAge,
        ),
      ],
    );
  }
}

import 'package:<APP_NAME>/widgets/drawer.dart';

class TransactionPage extends StatefulWidget {
const TransactionPage({Key? key}) : super(key: key);

@override
_TransactionPageState createState() => _TransactionPageState();
}

class _TransactionPageState extends State<TransactionPage> {
Future<List<TransactionRecord>> fetchTransactionRecord() async {
    // TODO: Ganti URL dan jangan lupa tambahkan trailing slash (/) di akhir URL!
    var url = Uri.parse(
        'https://<URL_APP_KAMU>/tracker/json/');
    var response = await http.get(
        url,
        headers: {"Content-Type": "application/json"},
    );

    // melakukan decode response menjadi bentuk json
    var data = jsonDecode(utf8.decode(response.bodyBytes));

    // melakukan konversi data json menjadi object TransactionRecord
    List<TransactionRecord> listTransactionRecord = [];
    for (var d in data) {
        if (d != null) {
            listTransactionRecord.add(TransactionRecord.fromJson(d));
        }
    }
    return listTransactionRecord;
}

@override
Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
        title: const Text('Transaksi'),
        ),
        drawer: const DrawerMenu(),
        body: FutureBuilder(
            future: fetchTransactionRecord(),
            builder: (context, AsyncSnapshot snapshot) {
                if (snapshot.data == null) {
                    return const Center(child: CircularProgressIndicator());
                } else {
                    if (!snapshot.hasData) {
                    return Column(
                        children:  [
                        Text(
                            "Tidak ada data transaksi.",
                            style:
                                TextStyle(color: Color(0xff59A5D8), fontSize: 20),
                        ),
                        SizedBox(height: 8),
                        ],
                    );
                } else {
                    return ListView.builder(
                        itemCount: snapshot.data!.length,
                        itemBuilder: (_, index) => Container(
                                margin: const EdgeInsets.symmetric(
                                    horizontal: 16, vertical: 12),
                                padding: const EdgeInsets.all(20.0),
                                decoration: BoxDecoration(
                                    color: Colors.white,
                                    borderRadius: BorderRadius.circular(15.0),
                                    boxShadow: [
                                    BoxShadow(
                                        color:
                                            snapshot.data![index].fields.type ==
                                                    "Pemasukan"
                                                ? Colors.blueAccent
                                                : Colors.red,
                                        blurRadius: 2.0)
                                    ]),
                                child: Column(
                                mainAxisAlignment: MainAxisAlignment.start,
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                    Text(
                                    "${snapshot.data![index].fields.name}",
                                    style: const TextStyle(
                                        fontSize: 18.0,
                                        fontWeight: FontWeight.bold,
                                    ),
                                    ),
                                    const SizedBox(height: 10),
                                    Text("${snapshot.data![index].fields.amount}"),
                                ],
                                ),
                            ));
                    }
                }
            }));
    }
}
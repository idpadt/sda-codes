// To parse this JSON data, do
//
//     final notes = notesFromJson(jsonString);

import 'dart:convert';

Notes notesFromJson(String str) => Notes.fromJson(json.decode(str));

String notesToJson(Notes data) => json.encode(data.toJson());

class Notes {
    String model;
    int pk;
    Fields fields;

    Notes({
        required this.model,
        required this.pk,
        required this.fields,
    });

    factory Notes.fromJson(Map<String, dynamic> json) => Notes(
        model: json["model"],
        pk: json["pk"],
        fields: Fields.fromJson(json["fields"]),
    );

    Map<String, dynamic> toJson() => {
        "model": model,
        "pk": pk,
        "fields": fields.toJson(),
    };
}

class Fields {
    String title;
    String description;
    DateTime date;
    String time;

    Fields({
        required this.title,
        required this.description,
        required this.date,
        required this.time,
    });

    factory Fields.fromJson(Map<String, dynamic> json) => Fields(
        title: json["title"],
        description: json["description"],
        date: DateTime.parse(json["date"]),
        time: json["time"],
    );

    Map<String, dynamic> toJson() => {
        "title": title,
        "description": description,
        "date": "${date.year.toString().padLeft(4, '0')}-${date.month.toString().padLeft(2, '0')}-${date.day.toString().padLeft(2, '0')}",
        "time": time,
    };
}

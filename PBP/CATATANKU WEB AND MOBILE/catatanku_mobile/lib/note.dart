class Note{
  String? title;
  String? description;

  Note({this.title, this.description});

  String? getTitle(){return title;}
  void setTitle(String title){this.title=title;}

  String? getDescription(){return description;}
  void setDescription(String description){this.description=description;}

}
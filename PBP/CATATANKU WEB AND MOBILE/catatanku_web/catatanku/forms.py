from django.forms import ModelForm
from catatanku.models import Notes

class NotesForm(ModelForm):
    class Meta:
        model = Notes
        fields = ["title", "description"]
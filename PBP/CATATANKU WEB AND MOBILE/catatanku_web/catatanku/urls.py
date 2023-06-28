from django.urls import path
from catatanku.views import show_main, create_note, show_json, show_json_by_id, big_boi

app_name = "catatanku"

urlpatterns = [
    path('', show_main, name = "show_main"),
    path('json/', show_json, name='show_json'),
    path('json/<int:id', show_json_by_id, name="show_json_by_id"),
    path('create/', create_note, name="create_note"),
    path('big_boi/', big_boi, name="big_boi")
]
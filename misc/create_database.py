import csv
import sqlite3
import os

categories = ["Animals", "Celebrities", "Entertainment", "General", "Geography", "History", "Hobbies", "Humanities", "Literature", "Movies"]


os.system("rm questions.sqlite")
conn = sqlite3.connect('questions.sqlite')
cur = conn.cursor()
cur.execute('CREATE TABLE categories (name TEXT, id INTEGER)')
cur.execute('CREATE TABLE questions (id INTEGER PRIMARY KEY, question TEXT, answer01 TEXT, answer02 TEXT, answer03 TEXT, answer04 TEXT, solution INTEGER, category INTEGER)')
conn.commit()

for id, category in enumerate(categories):
    cur.execute(f'INSERT INTO categories (name, id) values ("{category}", {id})')
    conn.commit()

    with open(f'questions/{category.lower()}.csv', newline='') as csvfile:
        reader = csv.reader(csvfile, delimiter=',')
        next(reader, None)
        for row in reader:
            question = row[1].replace("\n", "")
            answer01 = row[3]
            answer02 = row[4]
            answer03 = row[5]
            answer04 = row[6]
            solution = row[3:].index(row[2]) + 1

            cur.execute(f'INSERT INTO questions (id, question, answer01, answer02, answer03, answer04, solution, category) values (NULL, "{question}", "{answer01}", "{answer02}", "{answer03}", "{answer04}", {solution}, {id})')
            conn.commit()

conn.close()

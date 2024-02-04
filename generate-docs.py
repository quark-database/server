README_TEMPLATE_PATH = "../Readme-Template.md"
README_PATH = "../README.md"
MD_DOCS_TAG = "<!-- Quark:Docs -->"


def main():
    with open(README_TEMPLATE_PATH, 'r') as readme_template:
        readme_template_content = readme_template.read()
        readme_template_content.replace(MD_DOCS_TAG, '*It works!*')

    with open(README_PATH, 'w') as readme:
        readme.write(readme_template_content)


if __name__ == '__main__':
    main()

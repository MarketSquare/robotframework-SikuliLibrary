# -*- coding: utf-8 -*-
'''
Created on 2015/12/10

Author: by wang_yang1980@hotmail.com
'''
from setuptools import setup

from os.path import abspath, dirname, join
with open(join(dirname(abspath(__file__)), 'target', 'src', 'SikuliLibrary', 'version.py')) as f:
    exec(f.read())

with open("README.md", encoding="utf-8") as f:
    long_description = f.read()

DESCRIPTION = "Sikuli Robot Framework Library provide keywords for Robot Framework to test UI through Sikuli."

setup_kwargs = {
    "name": "robotframework-SikuliLibrary",
    "version": VERSION,
    "description": DESCRIPTION,
    "long_description": long_description,
    "long_description_content_type": "text/markdown",
    "author": "Wang Yang",
    "author_email": "wang_yang1980@hotmail.com",
    "maintainer": "Hélio Guilherme",
    "maintainer_email": "helioxentric@gmail.com",
    "url": "https://github.com/MarketSquare/robotframework-SikuliLibrary",
    "license": "Apache-2.0",
    "keywords": "robotframework testing testautomation sikuli UI",
    "platforms": "any",
    "package_dir": {"" : "target/src"},
    "packages": ["SikuliLibrary"],
    "package_data": {"SikuliLibrary": ["lib/*.jar",]},
    "python_requires": ">=3.8,<4.0",
    "classifiers": [
        "Development Status :: 5 - Production/Stable",
        "Operating System :: OS Independent",
        "Programming Language :: Java",
        "Programming Language :: Python :: 3",
        "Topic :: Software Development :: Testing",
        "Framework :: Robot Framework",
        "Framework :: Robot Framework :: Library",
    ],
    "include_package_data": True
}


setup(**setup_kwargs)


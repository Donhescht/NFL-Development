"""
Node.py

Created by Don Hescht on 13-Dec-2015
Copyright (c) 2015 DAHConsulting. All rights reserved.
"""


class Node(object):
    def __init__(self, identifier):
        self.__identifier=identifier
        self.__children=[]

    @property
    def identifier(self):
        return self.__identifier

    @property
    def children(self):
        return self.__children

    def add_child(self, identifier):
        self.__children.append(identifier)
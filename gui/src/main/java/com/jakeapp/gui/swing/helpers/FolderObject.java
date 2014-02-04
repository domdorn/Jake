package com.jakeapp.gui.swing.helpers;

import com.jakeapp.core.domain.FileObject;
import com.jakeapp.core.domain.Project;
import java.util.Collection;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents a folder in the file system
 * <p/>
 * TODO: This should eventually be moved to the domain objects
 */
public class FolderObject {
   private String relPath;
   private String name;
   private Project project;
   private List<FileObject> fileChildren = new ArrayList<FileObject>();
   private List<FolderObject> folderChildren = new ArrayList<FolderObject>();

   public FolderObject(String relPath, String name, Project project) {
      this.relPath = relPath;
      this.name = name;
      this.setProject(project);
   }

   public String getRelPath() {
      return relPath;
   }

   public String getName() {
      return name;
   }

   public List<FileObject> getFileChildren() {
      return fileChildren;
   }

   public List<FolderObject> getFolderChildren() {
      return folderChildren;
   }

   public List<Object> getAllChildren() {
      List<Object> ret = new ArrayList<Object>();
      ret.addAll(folderChildren);
      ret.addAll(fileChildren);

      return ret;
   }

   public void addFolder(FolderObject folder) {
      folderChildren.add(folder);
   }

   public void addFile(FileObject file) {
      fileChildren.add(file);
   }

   public void addFolders(List<FolderObject> folders) {
      folderChildren.addAll(folders);
   }

   public void addFiles(List<FileObject> files) {
      fileChildren.addAll(files);
   }

   public void setProject(Project project) {
	   this.project = project;
   }

   public Project getProject() {
	   return project;
   }

   /**
	 * Retrieves all FileObjects that are directly or indirectly under this
	 * FolderObject
	 * @return An empty list if fo is null or contains no files, a list of all files
	 * 	otherwise.
	 */
	public Collection<FileObject> flattenFolder() {
		Collection<FileObject> result = new ArrayList<FileObject>();

		result.addAll(getFileChildren());
		for (FolderObject childFolder : this.getFolderChildren())
			result.addAll(childFolder.flattenFolder());

		return result;
	}
}